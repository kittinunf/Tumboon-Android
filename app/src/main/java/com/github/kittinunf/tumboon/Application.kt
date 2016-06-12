package com.github.kittinunf.tumboon

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuse.core.Fuse
import java.net.HttpURLConnection

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()

        Fuse.init(cacheDir.path)
        FuelManager.instance.basePath = "http://10.0.3.2:8888"
        FuelManager.instance.addResponseInterceptor(
                { next: (Request, Response) -> Response ->
                    { req: Request, res: Response ->
                        if (res.httpStatusCode == HttpURLConnection.HTTP_OK) {
                            Fuse.bytesCache.put(req.url.toString(), res.data)
                        }
                        next(req, res)
                    }
                }
        )

    }

}


