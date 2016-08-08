package cromwell.services.keyvalue.impl

import com.typesafe.config.ConfigFactory
import cromwell.core.WorkflowSourceFiles
import cromwell.services.ServicesSpec

class KeyValueServiceActorSpec extends ServicesSpec("KeyValue") {

  val sources = WorkflowSourceFiles(
    wdlSource="""task a {command{}}
                |workflow w {
                |  call a
                |  call a as b
                |}
              """.stripMargin,
    inputsJson="{}",
    workflowOptionsJson="{}"
  )
  val config = ConfigFactory.empty()
  val kvServiceActor = KeyValueServiceActor(config, config)

  "KeyValueServiceActor" should {
    "insert a key/value" in {

    }

    "return error if key doesn't exist" ignore {

    }

    "be able to overwrite values" ignore {

    }

    "be able to store NULL values" ignore {

    }

    "partition keys by call" ignore {

    }

    "partition keys by workflow" ignore {

    }
  }
}
