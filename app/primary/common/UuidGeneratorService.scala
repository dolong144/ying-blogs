package primary.common

import business.services.common.IdGeneratorService
import java.util.UUID
import javax.inject.Singleton

@Singleton
class UuidGeneratorService extends IdGeneratorService {
  override def newId: String = UUID.randomUUID().toString
}
