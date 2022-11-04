package business.domain.post

import java.time.LocalDateTime


case class Post (
                id: PostId,
                title:String,
                content: String,
                author: String,
                thumbnail: String,
                createAt: LocalDateTime,
                modifyAt: LocalDateTime
                )
