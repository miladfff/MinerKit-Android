package com.minerkit.android.model

enum class MinerState { HEALTHY, WARNING, OFFLINE, UNKNOWN }

data class Miner(
    val id: String,
    val name: String,
    val model: String,
    val ip: String,
    val hashrate: Double,
    val expectedHashrate: Double,
    val temperature: Int,
    val powerWatts: Int,
    val state: MinerState,
    val pool: String
)

val demoMiners = listOf(
    Miner("1", "Miner 01", "WhatsMiner M50", "192.168.1.21", 118.4, 120.0, 67, 3290, MinerState.HEALTHY, "ViaBTC"),
    Miner("2", "Miner 02", "Antminer S19 XP", "192.168.1.22", 132.1, 140.0, 79, 3020, MinerState.WARNING, "F2Pool"),
    Miner("3", "Miner 03", "WhatsMiner M30S++", "192.168.1.23", 0.0, 112.0, 0, 0, MinerState.OFFLINE, "ViaBTC"),
    Miner("4", "Unknown device", "ASIC", "192.168.1.24", 0.0, 0.0, 0, 0, MinerState.UNKNOWN, "—")
)
