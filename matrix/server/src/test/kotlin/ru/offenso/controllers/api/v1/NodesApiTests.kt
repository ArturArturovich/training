package ru.offenso.controllers.api.v1

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import ru.offenso.PutNodeValues

@ImplicitReflectionSerializer
@RunWith(SpringRunner::class)
class NodesApiTests {

    @Test
    fun test() {
        val json = "{\"id\":1,\"fields\":[{\"id\":1,\"value\":\"123\"},{\"id\":2,\"value\":\"\"},{\"id\":3,\"value\":\"\"},{\"id\":4,\"value\":\"\"}]}"
        val v = Json.parse(PutNodeValues::class.serializer(), json)
        assertEquals(1, v.id)
    }

}