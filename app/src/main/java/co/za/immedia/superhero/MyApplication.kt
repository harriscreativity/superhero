package co.za.immedia.superhero

import android.app.Application
import co.za.immedia.superhero.Model.SuperHeroRealmModel
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where


class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this)
        // Open the realm for the UI thread.
    }

    fun InsertData(param: SuperHeroRealmModel,realm: Realm){

        realm.executeTransactionAsync({ bgRealm ->
            val SuperData = bgRealm.createObject<SuperHeroRealmModel>(param.id)
            SuperData.data = param.data
            print(SuperData.id)
            //SuperData.id = param.id
        }, {
            print("Inserted...")
        }, { error ->
            // Transaction failed and was automatically canceled
            print("Error occured : " + error.message)
        })

    }

    fun GetSuperHero(realm: Realm): ArrayList<SuperHeroRealmModel>? {

        var items = ArrayList<SuperHeroRealmModel>()

        val query = realm.where<SuperHeroRealmModel>()
        val result = query.findAll()

        for (s in result) {
            items.add(s)
        }

        return items
    }


}