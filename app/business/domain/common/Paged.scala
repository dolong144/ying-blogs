package business.domain.common

case class Paged[Entity](
                          total: Long,
                          items: Seq[Entity],
                          page: Long,
                          perPage: Int
                        ) {

  lazy val totalPages: Long = (total * 1.0  / perPage).ceil.toLong

  lazy val from: Long = (page - 1) * perPage
}
