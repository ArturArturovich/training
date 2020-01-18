package ru.offenso.repositories

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.PagingAndSortingRepository
import ru.offenso.entities.AuditEntity
import java.time.LocalDateTime

@NoRepositoryBean
interface CommonRepository<T : AuditEntity> : PagingAndSortingRepository<T, Long> {
    fun findAllByUpdatedAtAfter(updatedAt: LocalDateTime): Iterable<T>
}