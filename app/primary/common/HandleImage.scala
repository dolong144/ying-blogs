package primary.common

import play.api.libs.Files
import play.api.mvc.{MultipartFormData, Request}
import java.nio.file.Paths

class HandleImage {
  def imageUploaded(request: Request[MultipartFormData[Files.TemporaryFile]],
                    idGeneratorService: UuidGeneratorService): Option[String] = {
    request.body.file("thumbnail").map { image =>
      val fileExtension = image.contentType match {
        case Some("image/png") => ".png"
        case Some("image/jpeg") => ".jpg"
      }
      // Path of the thumbnail image
      val filePath = s"images/thumbnails/${idGeneratorService.newId}$fileExtension"
      // Save the image to the file system
      image.ref
        .copyTo(
          Paths.get(s"public/$filePath"),
          replace = true
        )
      filePath
    }
  }

}
