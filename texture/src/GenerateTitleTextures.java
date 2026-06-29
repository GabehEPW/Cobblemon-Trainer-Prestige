import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateTitleTextures {
    private static final int SCALE = 1;
    private static final int PAD_X = 4;
    private static final int PAD_Y = 2;
    private static final int OUTLINE = 1;
    private static final int ICON_GAP = 4;
    private static final int SPRITESHEET_COLUMNS = 8;
    private static final int FONT_RENDER_HEIGHT = 13;
    private static final int FONT_ASCENT = 10;
    private static final int FIRST_PRIVATE_CODEPOINT = 0xE500;
    private static final String RESOURCE_NAMESPACE = "cobblemon_trainer_prestige";

    private static final Pattern ID_PATTERN = Pattern.compile("id\\s*=\\s*\"([^\"]+)\"");
    private static final Pattern DISPLAY_PATTERN = Pattern.compile("displayName\\s*=\\s*\"([^\"]+)\"");
    private static final Pattern RARITY_PATTERN = Pattern.compile("rarity\\s*=\\s*TitleRarity\\.([A-Z_]+)");
    private static final Pattern CATEGORY_PATTERN = Pattern.compile("category\\s*=\\s*TitleCategory\\.([A-Z_]+)");
    private static final Pattern QUOTED_PATTERN = Pattern.compile("\"([^\"]+)\"");

    private record TitleTexture(String id, String fullName, String text, String rarity, String category, String fileName) {
    }

    private record GeneratedTitle(TitleTexture title, String glyph, int width, int height) {
    }

    private record Palette(String fill, String dark, String light, String outline, String text, String shadow, String icon) {
    }

    public static void main(String[] args) throws Exception {
        Path repo = repoRoot();
        Path output = repo.resolve("texture").resolve("generated");
        Path titlesOutput = output.resolve("titles");
        Path pack = repo.resolve("texture").resolve("resourcepack");
        Path packAssets = pack.resolve("assets").resolve(RESOURCE_NAMESPACE);
        Path packFont = packAssets.resolve("font");
        Path packFontTextures = packAssets.resolve("textures/font");
        Path packTitleTextures = packFontTextures.resolve("titles");
        Path minecraftFont = pack.resolve("assets/minecraft/font");
        Files.createDirectories(titlesOutput);
        Files.createDirectories(packFont);
        Files.createDirectories(packFontTextures);
        Files.createDirectories(packTitleTextures);
        Files.createDirectories(minecraftFont);

        List<TitleTexture> titles = new ArrayList<>();
        titles.addAll(readFixedTitles(repo));
        titles.addAll(readSpecialPokemonTitles(repo));
        titles.sort(Comparator.comparing(TitleTexture::rarity).thenComparing(TitleTexture::category).thenComparing(TitleTexture::id));

        List<BufferedImage> rendered = new ArrayList<>();
        List<GeneratedTitle> generatedTitles = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            TitleTexture title = titles.get(i);
            BufferedImage image = render(title);
            rendered.add(image);
            ImageIO.write(image, "png", titlesOutput.resolve(title.fileName()).toFile());
            ImageIO.write(image, "png", packTitleTextures.resolve(title.fileName()).toFile());
            generatedTitles.add(new GeneratedTitle(title, privateGlyph(i), image.getWidth(), image.getHeight()));
        }

        ImageIO.write(renderSpritesheet(rendered), "png", output.resolve("trainer_prestige_title_spritesheet.png").toFile());
        Files.writeString(output.resolve("title_textures_manifest.json"), manifestJson(generatedTitles), StandardCharsets.UTF_8);
        Files.writeString(output.resolve("title_batch_for_html.txt"), batchText(titles), StandardCharsets.UTF_8);
        Files.writeString(pack.resolve("pack.mcmeta"), packMeta(), StandardCharsets.UTF_8);
        Files.writeString(packFont.resolve("titles.json"), titleFontJson(generatedTitles), StandardCharsets.UTF_8);
        Files.writeString(minecraftFont.resolve("default.json"), defaultFontJson(generatedTitles), StandardCharsets.UTF_8);
        Files.writeString(pack.resolve("README.txt"), packReadme(), StandardCharsets.UTF_8);

        System.out.println("Generated " + titles.size() + " title textures in " + titlesOutput);
        System.out.println("Generated optional resource pack in " + pack);
    }

    private static Path repoRoot() {
        Path cwd = Paths.get("").toAbsolutePath().normalize();
        if (Files.exists(cwd.resolve("common")) && Files.exists(cwd.resolve("texture"))) {
            return cwd;
        }
        Path parent = cwd.getParent();
        if (parent != null && Files.exists(parent.resolve("common")) && Files.exists(parent.resolve("texture"))) {
            return parent;
        }
        throw new IllegalStateException("Run this generator from the repository root or the texture directory.");
    }

    private static List<TitleTexture> readFixedTitles(Path repo) throws IOException {
        Path registry = repo.resolve("common/src/main/kotlin/com/nbp/cobblemon_trainer_prestige/title/TitleRegistry.kt");
        List<String> lines = Files.readAllLines(registry, StandardCharsets.UTF_8);
        List<TitleTexture> titles = new ArrayList<>();
        String id = null;
        String display = null;
        String rarity = null;
        String category = null;

        for (String line : lines) {
            Matcher idMatcher = ID_PATTERN.matcher(line);
            if (idMatcher.find()) {
                id = idMatcher.group(1);
                display = null;
                rarity = null;
                category = null;
                continue;
            }

            Matcher displayMatcher = DISPLAY_PATTERN.matcher(line);
            if (displayMatcher.find()) {
                display = displayMatcher.group(1);
                continue;
            }

            Matcher rarityMatcher = RARITY_PATTERN.matcher(line);
            if (rarityMatcher.find()) {
                rarity = rarityMatcher.group(1);
                continue;
            }

            Matcher categoryMatcher = CATEGORY_PATTERN.matcher(line);
            if (categoryMatcher.find()) {
                category = categoryMatcher.group(1);
                if (id != null && display != null && rarity != null) {
                    titles.add(title(id, display, abbreviationFor(id, display, category), rarity, category));
                }
                id = null;
                display = null;
                rarity = null;
                category = null;
            }
        }

        return titles;
    }

    private static List<TitleTexture> readSpecialPokemonTitles(Path repo) throws IOException {
        Path classifier = repo.resolve("common/src/main/kotlin/com/nbp/cobblemon_trainer_prestige/cobblemon/SpecialPokemonClassifier.kt");
        String source = Files.readString(classifier, StandardCharsets.UTF_8);
        List<TitleTexture> titles = new ArrayList<>();

        for (String id : readSet(source, "LEGENDARY_IDS")) {
            String name = pokemonName(id);
            titles.add(title("mestre_de_" + id, "Master of " + name, name, "LEGENDARY", "LEGENDARY_MASTER"));
        }
        for (String id : readSet(source, "MYTHICAL_IDS")) {
            String name = pokemonName(id);
            titles.add(title("mestre_mitico_de_" + id, "Mythic Master of " + name, name, "MYTHIC", "MYTHICAL_MASTER"));
        }
        for (String id : readSet(source, "ULTRA_BEAST_IDS")) {
            String name = pokemonName(id);
            titles.add(title("mestre_ultra_de_" + id, "Ultra Master of " + name, name, "ULTRA_BEAST", "ULTRA_BEAST_MASTER"));
        }

        return titles;
    }

    private static List<String> readSet(String source, String setName) {
        int start = source.indexOf(setName + " = setOf(");
        if (start < 0) return List.of();
        int contentStart = source.indexOf('(', start) + 1;
        int contentEnd = source.indexOf("\n    )", contentStart);
        String content = source.substring(contentStart, contentEnd);
        List<String> values = new ArrayList<>();
        Matcher matcher = QUOTED_PATTERN.matcher(content);
        while (matcher.find()) {
            values.add(matcher.group(1));
        }
        return values;
    }

    private static TitleTexture title(String id, String fullName, String text, String rarity, String category) {
        return new TitleTexture(id, fullName, normalizeDisplayText(text), rarity, category, safeName(id) + ".png");
    }

    private static String abbreviationFor(String id, String display, String category) {
        Map<String, String> manual = Map.ofEntries(
            Map.entry("treinador_iniciante", "Beginner"),
            Map.entry("primeiro_parceiro", "Partner"),
            Map.entry("aprendiz_de_campo", "Field Appr"),
            Map.entry("pequeno_colecionador", "Collector"),
            Map.entry("treinador_casual", "Casual"),
            Map.entry("treinador_promissor", "Promising"),
            Map.entry("treinador_persistente", "Persistent"),
            Map.entry("aprendiz_de_estrategia", "Strategy"),
            Map.entry("treinador_experiente", "Experienced"),
            Map.entry("pesquisador_de_campo", "Researcher"),
            Map.entry("mestre_das_chamas", "Flames"),
            Map.entry("mestre_dos_eletricos", "Sparks"),
            Map.entry("guardiao_da_floresta", "Forest"),
            Map.entry("senhor_do_gelo", "Ice Lord"),
            Map.entry("senhor_da_terra", "Earth Lord"),
            Map.entry("mente_psiquica", "Psychic"),
            Map.entry("coracao_de_pedra", "Stone Heart"),
            Map.entry("treinador_fantasma", "Ghost"),
            Map.entry("senhor_das_sombras", "Shadows"),
            Map.entry("comandante_de_aco", "Steel Cmdr"),
            Map.entry("treinador_veterano", "Veteran"),
            Map.entry("mestre_da_pokedex", "Pokedex"),
            Map.entry("testemunha_lendaria", "Witness"),
            Map.entry("cuidador_de_ovos", "Egg Care"),
            Map.entry("criador_dedicado", "Breeder"),
            Map.entry("mestre_criador", "Master Breed"),
            Map.entry("lenda_da_criacao", "Breed Legend"),
            Map.entry("mestre_treinador", "Master"),
            Map.entry("domador_lendario", "Legend Tamer"),
            Map.entry("domador_mitico", "Mythic Tamer"),
            Map.entry("domador_ultra", "Ultra Tamer"),
            Map.entry("campeao_local", "Champion"),
            Map.entry("virada_impossivel", "Comeback"),
            Map.entry("lenda_da_pokedex", "Dex Legend"),
            Map.entry("guardiao_do_mundo", "Guardian"),
            Map.entry("lenda_brilhante", "Shiny Legend"),
            Map.entry("mestre_das_lendas", "Legends"),
            Map.entry("a_lenda_do_servidor", "Server Legend"),
            Map.entry("treinador_absoluto", "Absolute"),
            Map.entry("sem_pokebola_nao_da", "No Ball"),
            Map.entry("o_azarado", "Unlucky"),
            Map.entry("abencoado_pelo_rng", "RNG Blessed"),
            Map.entry("sofrimento_cromatico", "Chromatic"),
            Map.entry("mestre_do_nada", "Nothing"),
            Map.entry("perdido_no_mundo", "Lost"),
            Map.entry("fantasma_do_servidor", "Ghost"),
            Map.entry("o_escolhido", "Chosen"),
            Map.entry("primeiro_campeao", "Champion"),
            Map.entry("rival_publico", "Rival"),
            Map.entry("primeira_vitoria_pvp", "PvP Victory"),
            Map.entry("rival_de_respeito", "Rival"),
            Map.entry("sequencia_competitiva", "Streak"),
            Map.entry("amigo_de_batalha", "Battle Friend"),
            Map.entry("mestre_do_caos", "Chaos"),
            Map.entry("prestigio_maximo", "Max Prestige")
        );

        String value = manual.get(id);
        if (value != null) return value;
        if (category.endsWith("_MASTER")) return display.replaceFirst("^(Master of|Mythic Master of|Ultra Master of)\\s+", "");
        if (display.length() <= 15) return display;

        String shortened = display
            .replace("Trainer", "")
            .replace("Master", "")
            .replace("Legendary", "Legend")
            .replace("Pokemon", "")
            .replace("  ", " ")
            .trim();
        if (shortened.length() <= 15 && !shortened.isBlank()) return shortened;

        String[] words = display.split("\\s+");
        if (words.length > 1) {
            return words[words.length - 1].length() >= 5 ? words[words.length - 1] : words[0];
        }
        return display.substring(0, Math.min(15, display.length()));
    }

    private static String pokemonName(String showdownId) {
        Map<String, String> special = Map.ofEntries(
            Map.entry("hooh", "Ho-Oh"),
            Map.entry("typenull", "Type Null"),
            Map.entry("tapukoko", "Tapu Koko"),
            Map.entry("tapulele", "Tapu Lele"),
            Map.entry("tapubulu", "Tapu Bulu"),
            Map.entry("tapufini", "Tapu Fini"),
            Map.entry("wochien", "Wo-Chien"),
            Map.entry("chienpao", "Chien-Pao"),
            Map.entry("tinglu", "Ting-Lu"),
            Map.entry("chiyu", "Chi-Yu")
        );
        String mapped = special.get(showdownId);
        if (mapped != null) return mapped;
        return showdownId.substring(0, 1).toUpperCase(Locale.ROOT) + showdownId.substring(1);
    }

    private static BufferedImage render(TitleTexture title) {
        Palette palette = palette(title.rarity());
        String text = title.text().toUpperCase(Locale.ROOT);
        int textWidth = measure(text);
        int iconWidth = iconFor(title.category()).width();
        int contentWidth = iconWidth + ICON_GAP + textWidth;
        int bodyWidth = contentWidth + PAD_X * 2;
        int bodyHeight = 7 + PAD_Y * 2;
        int width = bodyWidth + OUTLINE * 2;
        int height = bodyHeight + OUTLINE * 2;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        int x = OUTLINE;
        int y = OUTLINE;

        g.setColor(color(palette.outline()));
        g.fillPolygon(tagShape(x - OUTLINE, y - OUTLINE, bodyWidth + OUTLINE * 2, bodyHeight + OUTLINE * 2));
        g.setColor(color(palette.fill()));
        g.fillPolygon(tagShape(x, y, bodyWidth, bodyHeight));
        g.setColor(color(palette.light()));
        g.fillRect(x + 1, y + 1, bodyWidth - 2, Math.max(1, bodyHeight / 3));
        g.setColor(color(palette.dark()));
        g.fillRect(x + 1, y + bodyHeight - 3, bodyWidth - 2, 2);

        int drawX = x + PAD_X;
        int drawY = y + PAD_Y;
        drawPattern(g, iconFor(title.category()).pattern(), drawX + 1, drawY + 1, new Color(0, 0, 0, 120));
        drawPattern(g, iconFor(title.category()).pattern(), drawX, drawY, color(palette.icon()));
        drawX += iconWidth + ICON_GAP;

        g.setColor(color(palette.outline()));
        g.fillRect(drawX - 2, y + 2, 1, bodyHeight - 4);

        drawText(g, text, drawX + 1, drawY + 1, titleTextShadow(title, palette));
        drawText(g, text, drawX, drawY, titleTextColor(title, palette));
        g.dispose();
        return image;
    }

    private static Polygon tagShape(int x, int y, int w, int h) {
        int cut = 1;
        Polygon polygon = new Polygon();
        polygon.addPoint(x + cut, y);
        polygon.addPoint(x + w - cut - 1, y);
        polygon.addPoint(x + w - 1, y + cut);
        polygon.addPoint(x + w - 1, y + h - cut - 1);
        polygon.addPoint(x + w - cut - 1, y + h - 1);
        polygon.addPoint(x + cut, y + h - 1);
        polygon.addPoint(x, y + h - cut - 1);
        polygon.addPoint(x, y + cut);
        return polygon;
    }

    private static BufferedImage renderSpritesheet(List<BufferedImage> images) {
        int rows = (int) Math.ceil(images.size() / (double) SPRITESHEET_COLUMNS);
        int[] colWidths = new int[SPRITESHEET_COLUMNS];
        int[] rowHeights = new int[rows];
        for (int i = 0; i < images.size(); i++) {
            BufferedImage image = images.get(i);
            int col = i % SPRITESHEET_COLUMNS;
            int row = i / SPRITESHEET_COLUMNS;
            colWidths[col] = Math.max(colWidths[col], image.getWidth());
            rowHeights[row] = Math.max(rowHeights[row], image.getHeight());
        }

        int width = 0;
        for (int colWidth : colWidths) width += colWidth + 2;
        int height = 0;
        for (int rowHeight : rowHeights) height += rowHeight + 2;

        BufferedImage sheet = new BufferedImage(Math.max(1, width), Math.max(1, height), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = sheet.createGraphics();
        int y = 0;
        for (int row = 0; row < rows; row++) {
            int x = 0;
            for (int col = 0; col < SPRITESHEET_COLUMNS; col++) {
                int index = row * SPRITESHEET_COLUMNS + col;
                if (index < images.size()) {
                    g.drawImage(images.get(index), x, y, null);
                }
                x += colWidths[col] + 2;
            }
            y += rowHeights[row] + 2;
        }
        g.dispose();
        return sheet;
    }

    private static Palette palette(String rarity) {
        return switch (rarity) {
            case "COMMON" -> new Palette("#F3F4F6", "#9CA3AF", "#FFFFFF", "#1F2937", "#111827", "#FFFFFF", "#2563EB");
            case "UNCOMMON" -> new Palette("#16A34A", "#166534", "#86EFAC", "#052E16", "#F8FFF9", "#052E16", "#FDE047");
            case "RARE" -> new Palette("#0284C7", "#075985", "#7DD3FC", "#082F49", "#F0FDFF", "#082F49", "#FDE047");
            case "EPIC" -> new Palette("#9333EA", "#581C87", "#D8B4FE", "#1E1B4B", "#FFFFFF", "#2E1065", "#FDE047");
            case "LEGENDARY" -> new Palette("#D97706", "#92400E", "#FDE68A", "#451A03", "#FFF7D6", "#451A03", "#FFFFFF");
            case "ULTRA_BEAST" -> new Palette("#5B21B6", "#312E81", "#C4B5FD", "#111827", "#FFFFFF", "#1E1B4B", "#FDE047");
            case "MYTHIC" -> new Palette("#DB2777", "#9D174D", "#F9A8D4", "#500724", "#FFFFFF", "#500724", "#FDE047");
            case "SECRET" -> new Palette("#7F1D1D", "#450A0A", "#FCA5A5", "#0F0A0A", "#FFE4E6", "#1F0A0A", "#FDE047");
            default -> new Palette("#64748B", "#334155", "#CBD5E1", "#0F172A", "#FFFFFF", "#1E293B", "#E2E8F0");
        };
    }

    private static Color titleTextColor(TitleTexture title, Palette palette) {
        return isPossessionMaster(title.category()) ? Color.WHITE : color(palette.text());
    }

    private static Color titleTextShadow(TitleTexture title, Palette palette) {
        return isPossessionMaster(title.category()) ? new Color(40, 12, 36) : color(palette.shadow());
    }

    private static boolean isPossessionMaster(String category) {
        return "LEGENDARY_MASTER".equals(category) ||
            "MYTHICAL_MASTER".equals(category) ||
            "ULTRA_BEAST_MASTER".equals(category);
    }

    private static Icon iconFor(String category) {
        return switch (category) {
            case "CAPTURE" -> Icon.POKEBALL;
            case "TYPE_MASTER" -> Icon.DIAMOND;
            case "SHINY" -> Icon.SPARKLE;
            case "BATTLE" -> Icon.SWORD;
            case "EXPLORATION" -> Icon.MAP;
            case "LEGENDARY", "LEGENDARY_MASTER" -> Icon.STAR;
            case "MYTHICAL", "MYTHICAL_MASTER" -> Icon.SPARKLE;
            case "ULTRA_BEAST", "ULTRA_BEAST_MASTER" -> Icon.DIAMOND;
            case "BREEDING" -> Icon.EGG;
            case "SOCIAL" -> Icon.HEART;
            case "EVENT" -> Icon.CROWN;
            case "SECRET" -> Icon.QUESTION;
            default -> Icon.SHIELD;
        };
    }

    private enum Icon {
        HEART("01010", "11111", "11111", "01110", "00100"),
        DIAMOND("00100", "01110", "11111", "01110", "00100"),
        STAR("00100", "10101", "01110", "11111", "01110", "10101", "00100"),
        SPARKLE("00100", "00100", "01110", "11111", "01110", "00100", "00100"),
        SWORD("00100", "00100", "00100", "00100", "01110", "00100", "01010"),
        EGG("01110", "10001", "10001", "10001", "10001", "01110"),
        MAP("11111", "10011", "10101", "11001", "11111"),
        CROWN("10001", "11011", "10101", "11111", "11111"),
        SHIELD("01110", "11111", "11111", "01110", "00100"),
        QUESTION("01110", "10001", "00001", "00010", "00100", "00000", "00100"),
        POKEBALL("01110", "10001", "11111", "00100", "01110");

        private final String[] pattern;

        Icon(String... pattern) {
            this.pattern = pattern;
        }

        int width() {
            int width = 0;
            for (String row : pattern) width = Math.max(width, row.length());
            return width * SCALE;
        }

        String[] pattern() {
            return pattern;
        }
    }

    private static final Map<Character, String[]> FONT = font();

    private static Map<Character, String[]> font() {
        Map<Character, String[]> font = new LinkedHashMap<>();
        font.put('A', rows("01110", "10001", "10001", "11111", "10001", "10001", "10001"));
        font.put('B', rows("11110", "10001", "10001", "11110", "10001", "10001", "11110"));
        font.put('C', rows("01111", "10000", "10000", "10000", "10000", "10000", "01111"));
        font.put('D', rows("11110", "10001", "10001", "10001", "10001", "10001", "11110"));
        font.put('E', rows("11111", "10000", "10000", "11110", "10000", "10000", "11111"));
        font.put('F', rows("11111", "10000", "10000", "11110", "10000", "10000", "10000"));
        font.put('G', rows("01111", "10000", "10000", "10111", "10001", "10001", "01111"));
        font.put('H', rows("10001", "10001", "10001", "11111", "10001", "10001", "10001"));
        font.put('I', rows("11111", "00100", "00100", "00100", "00100", "00100", "11111"));
        font.put('J', rows("00111", "00010", "00010", "00010", "00010", "10010", "01100"));
        font.put('K', rows("10001", "10010", "10100", "11000", "10100", "10010", "10001"));
        font.put('L', rows("10000", "10000", "10000", "10000", "10000", "10000", "11111"));
        font.put('M', rows("10001", "11011", "10101", "10101", "10001", "10001", "10001"));
        font.put('N', rows("10001", "11001", "10101", "10011", "10001", "10001", "10001"));
        font.put('O', rows("01110", "10001", "10001", "10001", "10001", "10001", "01110"));
        font.put('P', rows("11110", "10001", "10001", "11110", "10000", "10000", "10000"));
        font.put('Q', rows("01110", "10001", "10001", "10001", "10101", "10010", "01101"));
        font.put('R', rows("11110", "10001", "10001", "11110", "10100", "10010", "10001"));
        font.put('S', rows("01111", "10000", "10000", "01110", "00001", "00001", "11110"));
        font.put('T', rows("11111", "00100", "00100", "00100", "00100", "00100", "00100"));
        font.put('U', rows("10001", "10001", "10001", "10001", "10001", "10001", "01110"));
        font.put('V', rows("10001", "10001", "10001", "10001", "10001", "01010", "00100"));
        font.put('W', rows("10001", "10001", "10001", "10101", "10101", "10101", "01010"));
        font.put('X', rows("10001", "10001", "01010", "00100", "01010", "10001", "10001"));
        font.put('Y', rows("10001", "10001", "01010", "00100", "00100", "00100", "00100"));
        font.put('Z', rows("11111", "00001", "00010", "00100", "01000", "10000", "11111"));
        font.put('0', rows("01110", "10001", "10011", "10101", "11001", "10001", "01110"));
        font.put('1', rows("00100", "01100", "00100", "00100", "00100", "00100", "01110"));
        font.put('2', rows("01110", "10001", "00001", "00010", "00100", "01000", "11111"));
        font.put('3', rows("11110", "00001", "00001", "01110", "00001", "00001", "11110"));
        font.put('4', rows("10010", "10010", "10010", "11111", "00010", "00010", "00010"));
        font.put('5', rows("11111", "10000", "10000", "11110", "00001", "00001", "11110"));
        font.put('6', rows("01111", "10000", "10000", "11110", "10001", "10001", "01110"));
        font.put('7', rows("11111", "00001", "00010", "00100", "01000", "01000", "01000"));
        font.put('8', rows("01110", "10001", "10001", "01110", "10001", "10001", "01110"));
        font.put('9', rows("01110", "10001", "10001", "01111", "00001", "00001", "11110"));
        font.put(' ', rows("00000", "00000", "00000", "00000", "00000", "00000", "00000"));
        font.put('-', rows("00000", "00000", "00000", "11111", "00000", "00000", "00000"));
        font.put('?', rows("01110", "10001", "00001", "00010", "00100", "00000", "00100"));
        return font;
    }

    private static String[] rows(String... rows) {
        return rows;
    }

    private static int measure(String text) {
        int width = 0;
        for (char ch : text.toCharArray()) {
            String[] glyph = FONT.getOrDefault(ch, FONT.get('?'));
            width += glyph[0].length() * SCALE + SCALE;
        }
        return Math.max(0, width - SCALE);
    }

    private static void drawText(Graphics2D g, String text, int x, int y, Color color) {
        g.setColor(color);
        for (char ch : text.toCharArray()) {
            String[] glyph = FONT.getOrDefault(ch, FONT.get('?'));
            drawPattern(g, glyph, x, y, color);
            x += (glyph[0].length() + 1) * SCALE;
        }
    }

    private static void drawPattern(Graphics2D g, String[] pattern, int x, int y, Color color) {
        g.setColor(color);
        for (int row = 0; row < pattern.length; row++) {
            for (int col = 0; col < pattern[row].length(); col++) {
                if (pattern[row].charAt(col) == '1') {
                    g.fillRect(x + col * SCALE, y + row * SCALE, SCALE, SCALE);
                }
            }
        }
    }

    private static Color color(String hex) {
        return Color.decode(hex);
    }

    private static String normalizeDisplayText(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "")
            .replace(":", "")
            .replace(".", "")
            .replace("'", "")
            .replaceAll("\\s+", " ")
            .trim();
        return normalized;
    }

    private static String safeName(String id) {
        return id.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_\\-]+", "_");
    }

    private static String manifestJson(List<GeneratedTitle> titles) {
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        for (int i = 0; i < titles.size(); i++) {
            GeneratedTitle generated = titles.get(i);
            TitleTexture title = generated.title();
            json.append("  {\n");
            json.append("    \"id\": \"").append(escape(title.id())).append("\",\n");
            json.append("    \"fullName\": \"").append(escape(title.fullName())).append("\",\n");
            json.append("    \"textureText\": \"").append(escape(title.text())).append("\",\n");
            json.append("    \"rarity\": \"").append(escape(title.rarity())).append("\",\n");
            json.append("    \"category\": \"").append(escape(title.category())).append("\",\n");
            json.append("    \"glyph\": \"").append(escape(generated.glyph())).append("\",\n");
            json.append("    \"glyphCodepoint\": \"").append(codepoint(generated.glyph())).append("\",\n");
            json.append("    \"file\": \"titles/").append(escape(title.fileName())).append("\",\n");
            json.append("    \"resourcePackFile\": \"assets/").append(RESOURCE_NAMESPACE).append("/textures/font/titles/")
                .append(escape(title.fileName())).append("\",\n");
            json.append("    \"width\": ").append(generated.width()).append(",\n");
            json.append("    \"height\": ").append(generated.height()).append("\n");
            json.append("  }");
            if (i < titles.size() - 1) json.append(",");
            json.append("\n");
        }
        json.append("]\n");
        return json.toString();
    }

    private static String titleFontJson(List<GeneratedTitle> titles) {
        return fontJson(titles, false);
    }

    private static String defaultFontJson(List<GeneratedTitle> titles) {
        return fontJson(titles, true);
    }

    private static String fontJson(List<GeneratedTitle> titles, boolean includeVanillaReferences) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"providers\": [\n");
        for (int i = 0; i < titles.size(); i++) {
            GeneratedTitle generated = titles.get(i);
            TitleTexture title = generated.title();
            json.append("    {\n");
            json.append("      \"type\": \"bitmap\",\n");
            json.append("      \"file\": \"").append(RESOURCE_NAMESPACE).append(":font/titles/")
                .append(escape(title.fileName())).append("\",\n");
            json.append("      \"ascent\": ").append(FONT_ASCENT).append(",\n");
            json.append("      \"height\": ").append(FONT_RENDER_HEIGHT).append(",\n");
            json.append("      \"chars\": [\"").append(jsonUnicode(generated.glyph())).append("\"]\n");
            json.append("    }");
            json.append(",");
            json.append("\n");
        }
        if (!includeVanillaReferences) {
            json.append("    {\n");
            json.append("      \"type\": \"space\",\n");
            json.append("      \"advances\": {\n");
            json.append("        \" \": 4,\n");
            json.append("        \"\\u200c\": 0\n");
            json.append("      }\n");
            json.append("    }\n");
        }
        if (includeVanillaReferences) {
            json.append("    {\n");
            json.append("      \"type\": \"reference\",\n");
            json.append("      \"id\": \"minecraft:include/space\"\n");
            json.append("    },\n");
            json.append("    {\n");
            json.append("      \"type\": \"reference\",\n");
            json.append("      \"id\": \"minecraft:include/default\",\n");
            json.append("      \"filter\": {\n");
            json.append("        \"uniform\": false\n");
            json.append("      }\n");
            json.append("    },\n");
            json.append("    {\n");
            json.append("      \"type\": \"reference\",\n");
            json.append("      \"id\": \"minecraft:include/unifont\"\n");
            json.append("    }\n");
        }
        json.append("  ]\n");
        json.append("}\n");
        return json.toString();
    }

    private static String packMeta() {
        return """
            {
              "pack": {
                "pack_format": 34,
                "description": "Trainer Prestige Resource"
              }
            }
            """;
    }

    private static String packReadme() {
        return """
            Trainer Prestige Optional Title Textures

            This resource pack is optional.
            The mod can keep using normal text titles without this pack.

            Textured titles use private-use glyphs registered in cobblemon_trainer_prestige:titles.
            The same glyphs are also registered in the default Minecraft font for compatibility.
            The glyph mapping is stored in texture/generated/title_textures_manifest.json.

            Keep this pack separate from the mod jar so servers and players can choose whether they want textured titles.
            """;
    }

    private static String privateGlyph(int index) {
        return new String(Character.toChars(FIRST_PRIVATE_CODEPOINT + index));
    }

    private static String codepoint(String glyph) {
        return String.format("U+%04X", glyph.codePointAt(0));
    }

    private static String jsonUnicode(String glyph) {
        return String.format("\\u%04X", glyph.codePointAt(0));
    }

    private static String batchText(List<TitleTexture> titles) {
        StringBuilder batch = new StringBuilder();
        for (TitleTexture title : titles) {
            Palette palette = palette(title.rarity());
            batch.append(title.text()).append(" | ").append(palette.fill()).append(" | ")
                .append(iconName(title.category())).append(" | ").append(palette.icon()).append("\n");
        }
        return batch.toString();
    }

    private static String iconName(String category) {
        return switch (iconFor(category)) {
            case HEART -> "heart";
            case DIAMOND -> "diamond";
            case STAR -> "star";
            case SPARKLE -> "sparkle";
            case SWORD -> "sword";
            case EGG -> "egg";
            case MAP -> "map";
            case CROWN -> "crown";
            case SHIELD -> "shield";
            case QUESTION -> "text:?";
            case POKEBALL -> "pokeball";
        };
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
