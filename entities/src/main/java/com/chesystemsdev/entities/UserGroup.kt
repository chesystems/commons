package com.chesystemsdev.entities

/** Represents a collection of user references by their IDs */
data class UserGroup(
    val userRefs: List<String> = emptyList()
) {
    companion object {
        /** Creates a new UserRefs from a list of user IDs */
        fun from(vararg refs: String): UserGroup {
            return UserGroup(refs.toList())
        }

        /** Creates a new UserRefs from a collection of UserProfiles */
        fun fromProfiles(profiles: Collection<User>): UserGroup {
            return UserGroup(profiles.map { it.id })
        }
    }
}