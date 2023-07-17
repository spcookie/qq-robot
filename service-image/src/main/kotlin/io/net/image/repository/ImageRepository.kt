package io.net.image.repository;

import io.net.image.entity.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : JpaRepository<Image, Long>, JpaSpecificationExecutor<Image> {

    fun findFirstByCategoryIsOrderByCreatedDateAsc(category: Image.Category): Image?

    fun countImagesByCategoryIs(category: Image.Category): Int
}