package gitlin.kothub.github.api.data

import gitlin.kothub.utilities.*
import org.json.JSONArray
import org.json.JSONObject


data class UserSummary(private val viewer: JSONObject?) {

    val avatarUrl: String? by viewer
    val login: String? by viewer
    val name: String? by viewer
    val websiteUrl: String? by viewer
    val bio: String? by viewer
    val url: String? by viewer
    val company: String? by viewer
    val location: String? by viewer
    val followers : Int? = viewer.totalCount("followers")
    val following : Int? = viewer.totalCount("following")
    val starredRepositories : Int? = viewer.totalCount("starredRepositories")
    val repositories : Int? = viewer.totalCount("repositories")
    val pinnedRepositories =
            viewer.obj("pinnedRepositories")?.arr("nodes")?.map<JSONObject, PinnedRepository> { PinnedRepository(it) }
            ?: arrayListOf()
}


data class PinnedRepository(val json: JSONObject) {
    val name: String? by json
    val description: String? by json
}

