package co.za.immedia.superhero.Model

import co.za.immedia.superhero.Model.SuperHeroModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SuperHeroRealmModel(
    @PrimaryKey var id: Long = 0,
    var data: String? = null
):RealmObject()

