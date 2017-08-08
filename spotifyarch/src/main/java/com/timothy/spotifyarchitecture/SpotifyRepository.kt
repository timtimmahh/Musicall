package com.timothy.spotifyarchitecture

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.WorkerThread
import android.util.Log
import com.timothy.spotifyarchitecture.entities.SpotifyUser
import com.timothy.spotifyarchitecture.entities.Token
import com.timothy.spotifyarchitecture.livedata.TokenLiveData
import com.timothy.spotifyarchitecture.remote.Resource
import com.timothy.spotifyarchitecture.retrofit.*
import com.timothy.spotifyarchitecture.retrofit.models.UserPrivate
import com.timothy.spotifyarchitecture.room.SpotifyDao
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

/**
 * Repository to manage network and database
 */
@Singleton
class SpotifyRepository
@Inject
constructor(var serviceDao: SpotifyDao, var spotifyService: SpotifyService, var spotifyServiceAuth: SpotifyServiceAuth, var apiAuthenticator: SpotifyCreator.ApiAuthenticator) {
	
	fun updateApiAuthenticator(accessToken: String? = "") {
		if (notNullEmpty(accessToken))
			apiAuthenticator.accessToken = accessToken !!
	}
	
	fun obtainTokenResource(id: String = "", accessToken: String = "", code: String = "", onCompletion: (MediatorLiveData<Resource<Token>>) -> Unit = {}): AutoResourceObtainer<Token, Token> {
		return object : AutoResourceObtainer<Token, Token>(onCompletion) {
			override fun loadResource(value: Token?): Token? {
				val userId = value?.userId ?: id
				val authToken = value?.accessToken ?: accessToken
				val token: Token? =
						if (notNullEmpty(userId) || notEmpty(authToken))
							serviceDao.loadAuthToken(userId, authToken)
						else null
				updateApiAuthenticator(authToken)
				log("loadFromDb: ${toString(token)}")
				return token
			}
			
			override fun shouldFetchResource(value: Token?): Boolean {
				return isNull(value) || TokenLiveData.needsRefresh()
			}
			
			override fun callMethod(): Call<Token> {
				val body = HashMap<String, String>()
				if (TokenLiveData.needsRefresh() && empty(code)) {
					body.put("grant_type", "refresh_token")
					body.put("refresh_token", TokenLiveData.tInstance.value?.refreshToken !!)
				} else if (notEmpty(code)) {
					body.put("grant_type", "authorization_code")
					body.put("code", code)
					body.put("redirect_uri", Auth.REDIRECT_URI)
				} else logE("Trying to fetch token with empty code and unnecessary refresh")
				body.put("client_id", Auth.SPOTIFY_CLIENT_ID)
				body.put("client_secret", Auth.SPOTIFY_CLIENT_SECRET)
				return spotifyServiceAuth.getToken(body)
			}
			
			override fun onFetchFailure(call: Call<Token>, error: SpotifyError) {
			
			}
			
			override fun convertRequestToResultType(item: Token): Token {
				return item
			}
			
			override fun saveResource(item: Token) {
				if (isNull(item)) return
				item.expiresIn = System.currentTimeMillis() + item.expiresIn * 1000
				updateApiAuthenticator(item.accessToken)
				serviceDao.saveToken(item)
			}
			
			override fun updateResource(item: Token) {
				if (notNull(item))
					serviceDao.updateToken(item)
			}
		}
	}
	
	/*private val tokenResource: MediatorLiveData<Resource<Token>> = MediatorLiveData()
	fun getToken(id: String = "", accessToken: String = "", code: String = ""): MediatorLiveData<Resource<Token>> {
		log("getting token...")
		val tokenData: MutableLiveData<Token> = MutableLiveData()
		tokenResource.value = Resource.loading(null)
		tokenData.value = loadToken(id, accessToken)
		if (nullEmpty(tokenData.value?.access_token)) tokenData.value = null
		log("loaded token from db...")
		tokenResource.addSource(tokenData) { dbToken ->
			log("Updated tokenData")
			tokenResource.removeSource(tokenData)
			if (shouldFetchToken(tokenData.value)) {
				log("fetching new token...")
				tokenResource.value = Resource.loading(dbToken)
				Observable.just(fetchToken(code))
						.subscribeOn(Schedulers.io())
						.subscribe()
			} else dbToken?.let {
				tokenResource.value = Resource.success(dbToken); log("keeping token from db")
			}
		}
		log("returning livedata for observing")
		return tokenResource
	}*/
	
	fun obtainMeResource(id: String = "", accessToken: String = "", onCompletion: (MediatorLiveData<Resource<SpotifyUser>>) -> Unit = {}): AutoResourceObtainer<SpotifyUser, UserPrivate> {
		return object : AutoResourceObtainer<SpotifyUser, UserPrivate>(onCompletion) {
			override fun loadResource(value: SpotifyUser?): SpotifyUser? {
				val userId = value?.id ?: id
				val authToken = value?.accessToken ?: accessToken
				val user: SpotifyUser? =
						if (notEmpty(userId) || notEmpty(authToken))
							serviceDao.loadMe(userId, accessToken)
						else null
				log("loadFromDb:" + toString(user))
				return user
			}
			
			override fun shouldFetchResource(value: SpotifyUser?): Boolean {
				return true
			}
			
			override fun callMethod(): Call<UserPrivate> {
				return spotifyService.me
			}
			
			override fun onFetchFailure(call: Call<UserPrivate>, error: SpotifyError) {
			
			}
			
			override fun convertRequestToResultType(item: UserPrivate): SpotifyUser {
				return SpotifyUser(accessToken, item)
			}
			
			override fun saveResource(item: SpotifyUser) {
				if (notNull(item))
					serviceDao.saveMe(item)
			}
			
			override fun updateResource(item: SpotifyUser) {
				if (notNull(item))
					serviceDao.updateMe(item)
			}
			
		}
	}
	
	/*fun obtainMyAlbums(id: String = "", onCompletion: (MediatorLiveData<Resource<List<SpotifyUserAlbums>>>) -> Unit): AutoResourceObtainer<List<SpotifyUserAlbums>, Pager<SavedAlbum>> {
		return object : AutoResourceObtainer<List<SpotifyUserAlbums>, Pager<SavedAlbum>>(onCompletion) {
			override fun onFetchFailure(call: Call<Pager<SavedAlbum>>, error: SpotifyError) {

			}

			override fun convertRequestToResultType(item: Pager<SavedAlbum>): List<SpotifyUserAlbums> {
                return item.items.map { SpotifyUserAlbums(0,id, it.album.id) }
			}

			override fun loadResource(value: List<SpotifyUserAlbums>?): List<SpotifyUserAlbums>? {
				return serviceDao.loadMySavedAlbums(id)
			}

			override fun shouldFetchResource(value: List<SpotifyUserAlbums>?): Boolean {
				return true
			}

			override fun callMethod(): Call<Pager<SavedAlbum>> {
				return spotifyService.mySavedAlbums
			}

			override fun saveResource(item: List<SpotifyUserAlbums>) {
				if (notNull(item)) {
					serviceDao.insertAlbums(item)
					serviceDao.insertAllUserAlbums(item.map { SpotifyUserAlbums(0, it.userId, it.albumId) })
				}
			}

			override fun updateResource(item: List<SpotifyUserAlbums>) {

			}

		}
	}*/
	
}

abstract class AutoResourceObtainer<ResultType : Any, RequestType>(val onCompletion: (MediatorLiveData<Resource<ResultType>>) -> Unit) {
	protected val resource: MediatorLiveData<Resource<ResultType>> = MediatorLiveData()
	val logLevel: Int = Log.DEBUG
	
	fun obtainResource(): MediatorLiveData<Resource<ResultType>> {
		resource.value = Resource.loading(null)
		if (logLevel <= Log.DEBUG) log("getting ${resource.javaClass.simpleName}...")
		val resourceData = loadResourceFromDb()
		if (logLevel <= Log.DEBUG) log("loaded ${resource.javaClass.simpleName} from db...")
		resource.addSource(resourceData) { dbResource ->
			if (logLevel <= Log.DEBUG) log("Updated ${resourceData::class.java.simpleName}")
			resource.removeSource(resourceData)
			if (shouldFetchResource(dbResource)) {
				if (logLevel <= Log.DEBUG) log("fetching new ${resourceData::class.java.simpleName}...")
				resource.value = Resource.loading(dbResource)
				Observable.just(fetchResource())
						.subscribeOn(Schedulers.io())
						.subscribe()
			} else dbResource?.let {
				resource.value = Resource.success(dbResource); if (logLevel <= Log.DEBUG) log("keeping ${resource::class.java.simpleName} from db")
			}
		}
		if (logLevel <= Log.DEBUG) log("returning livedata for observing")
		return resource
	}
	
	fun loadResourceFromDb(): MutableLiveData<ResultType> {
		
		val dataSource = MutableLiveData<ResultType>()
		Observable.create<ResultType> { subscriber ->
			thread {
				dataSource.postValue(loadResource(null))
				subscriber.onComplete()
			}
		}.observeOn(AndroidSchedulers.mainThread())
				.subscribe()
		return dataSource
	}
	
	abstract fun loadResource(value: ResultType?): ResultType?
	
	abstract fun shouldFetchResource(value: ResultType?): Boolean
	
	private fun fetchResource() {
		if (logLevel <= Log.DEBUG) log("create network request...")
		callMethod().enqueue(object : SpotifyCallback<RequestType>() {
			override fun onResponse(call: Call<RequestType>, response: Response<RequestType>, payload: RequestType) {
				saveResultResource(convertRequestToResultType(payload))
			}
			
			override fun onFailure(call: Call<RequestType>, error: SpotifyError) {
				onFetchFailure(call, error)
				resource.value = Resource.error(error.message, resource.value?.data)
				if (logLevel <= Log.ERROR) logE("Failed to fetch ${error.message}")
			}
			
		})
	}
	
	abstract fun callMethod(): Call<RequestType>
	
	abstract fun onFetchFailure(call: Call<RequestType>, error: SpotifyError)
	
	private fun saveResultResource(item: ResultType) {
		if (logLevel <= Log.DEBUG) log("saveCallResult: ${com.timothy.spotifyarchitecture.toString(item)}")
		item.let {
			Observable.create<ResultType> { subscriber ->
				thread {
					if (logLevel <= Log.DEBUG) log("saving resource...")
					saveResource(item)
					if (logLevel <= Log.DEBUG) {
						log("resource saved..."); log("reloading resource from db...")
					}
					val obj = loadResource(item)
					if (obj == null) subscriber.onError(NullPointerException("Resource obtained from database is null"))
					else subscriber.onNext(obj)
				}
			}.observeOn(AndroidSchedulers.mainThread())
					.subscribeBy(onError = {
						if (logLevel <= Log.DEBUG) {
							log("error: ${it.message}")
						}; resource.value = Resource.error(it.message, resource.value?.data)
					},
					             onComplete = { if (logLevel <= Log.DEBUG) log("token loaded", "LoadToken"); onCompletion.invoke(resource) }) { resource.value = Resource.success(it) }
		}
	}
	
	fun updateResultResource(item: ResultType) {
		if (logLevel <= Log.DEBUG) log("saveCallResult: ${com.timothy.spotifyarchitecture.toString(item)}")
		item.let {
			Observable.create<ResultType> { subscriber ->
				thread {
					if (logLevel <= Log.DEBUG) log("updating resource...")
					updateResource(item)
					if (logLevel <= Log.DEBUG) {
						log("resource updated..."); log("reloading resource from db...")
					}
					val obj = loadResource(item)
					if (obj == null) subscriber.onError(NullPointerException("Resource obtained from database is null"))
					else subscriber.onNext(obj)
				}
			}.observeOn(AndroidSchedulers.mainThread())
					.subscribeBy(onError = {
						if (logLevel <= Log.DEBUG) {
							log("error: ${it.message}")
						}; resource.value = Resource.error(it.message, resource.value?.data)
					},
					             onComplete = { if (logLevel <= Log.DEBUG) log("token loaded", "LoadToken"); onCompletion.invoke(resource) }) { resource.value = Resource.success(it) }
		}
	}
	
	abstract fun convertRequestToResultType(item: RequestType): ResultType
	
	@WorkerThread
	abstract fun saveResource(item: ResultType)
	
	@WorkerThread
	abstract fun updateResource(item: ResultType)
	
	fun onObtainComplete() {
		onCompletion.invoke(resource)
	}
}