package com.nbp.cobblemon_trainer_prestige.title

data class TitleRequirement(
    val progressKey: String,
    val target: Int,
    val operator: Operator = Operator.AT_LEAST,
) {
    enum class Operator {
        AT_LEAST,
        TRUE,
    }

    fun isComplete(progress: Map<String, Int>): Boolean {
        val value = progress[progressKey] ?: 0
        return when (operator) {
            Operator.AT_LEAST -> value >= target
            Operator.TRUE -> value >= 1
        }
    }

    fun current(progress: Map<String, Int>): Int = progress[progressKey] ?: 0
}
