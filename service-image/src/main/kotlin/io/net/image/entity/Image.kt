package io.net.image.entity

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
@Table(name = "service_image")
open class Image(
    @Column(length = 100)
    open var name: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    open var category: Category? = null,

    @Column(length = 100, nullable = false)
    open var path: String? = null,

    @Column(length = 255)
    open var url: String? = null
) : Audit() {

    enum class Category { GIRL, PIXIV }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Image

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return "Image(name=$name, category=$category, path=$path, url=$url)"
    }
}