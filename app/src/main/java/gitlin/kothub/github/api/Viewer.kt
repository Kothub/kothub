package gitlin.kothub.github.api

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result
import gitlin.kothub.github.api.data.UserSummary
import gitlin.kothub.github.api.dsl.query
import gitlin.kothub.utilities.obj



fun userSummary (callback: (FuelError?, UserSummary?) -> Unit) {
    post(
        query {
            viewer {
                avatarUrl
                login
                bio
                company
                location
                followers { totalCount }
                name
                websiteUrl
                url
                pinnedRepositories(6) {
                    nodes {
                        name
                        description
                    }
               }
            }
        }
    )
    .responseJson { request, response, result ->
        when (result) {
            is Result.Failure -> callback(result.error, null)
            is Result.Success -> callback(null, UserSummary(result.value.obj().obj("data")!!))
        }
    }

}