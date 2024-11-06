package com.chesystems.entities

/** Represents a group of users */
interface UserGroup<ID> {
    /** Unique identifier for this group */
    val id: String
    
    /** List of user IDs in this group */
    val userRefs: List<ID>

    companion object {
        /** Creates a new UserGroup implementation from a list of user IDs */
        fun <ID> from(id: String, vararg refs: ID): UserGroup<ID> {
            return SimpleUserGroup(id, refs.toList())
        }

        /** Creates a new UserGroup implementation from a collection of Users */
        fun <ID, M> fromProfiles(id: String, profiles: Collection<User<ID, M>>): UserGroup<ID> {
            return SimpleUserGroup(id, profiles.map { it.id })
        }
    }
}

/** Basic implementation of UserGroup */
private data class SimpleUserGroup<ID>(
    override val id: String,
    override val userRefs: List<ID> = emptyList()
) : UserGroup<ID>