package co.za.immedia.superhero.Model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class SuperHeroRealmModel(
    @PrimaryKey var id: Int? = null,
    var data: String? = null
):RealmObject()

