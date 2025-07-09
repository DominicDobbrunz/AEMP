package de.syntax.aemp.data.model


data class Device(
    val id: Int,
    val name: String,
    val category: String,
    val details: String,
    val image: String
) {
    constructor() : this(0, "", "", "", "")
}
