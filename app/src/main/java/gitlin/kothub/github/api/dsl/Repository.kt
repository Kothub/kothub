package gitlin.kothub.github.api.dsl

enum class RepositoryPrivacy (val value: String){
    PUBLIC("PUBLIC"),
    PRIVATE("PRIVATE")
}

class Repository(override val level: Int): Element {

    override val fields = arrayListOf<Field>()

    val description: Unit get() = addField("description")
    val name: Unit get() = addField("name")

    fun issues(first: Int = 10, body: IssueConnection.() -> Unit) {
        val connection = IssueConnection(nextLevel())
        connection.body()

        val pagination = paginationData(first)
        addField(Node("issues($pagination)", connection.fields))
    }
}

class RepositoryConnection(override val level: Int) : Connection<Repository>(level) {
    fun edges (body: RepositoryEdge.() -> Unit) {
        val edges = RepositoryEdge(nextLevel())
        edges.body()
        addField(Node("edges", edges.fields))
    }

    fun nodes (body: Repository.() -> Unit) {
        val repo = Repository(nextLevel())
        repo.body()
        addField(Node("nodes", repo.fields))
    }
}


class RepositoryEdge(override val level: Int): Edges(level) {
    fun node (body: Repository.() -> Unit) {
        val repo = Repository(nextLevel())
        repo.body()
        addField(Node(fields = repo.fields))
    }
}
