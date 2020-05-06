package com.example.headspacecodechallenge.model


data class ImageResponse(
    val imageItems: Array<ImageItem>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageResponse

        if (!imageItems.contentEquals(other.imageItems)) return false

        return true
    }

    override fun hashCode(): Int {
        return imageItems.contentHashCode()
    }
}