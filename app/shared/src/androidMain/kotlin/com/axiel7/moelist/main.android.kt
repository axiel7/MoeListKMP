package com.axiel7.moelist

import android.content.Context
import com.axiel7.moelist.data.local.createDataStore
import com.axiel7.moelist.data.local.getDatabaseBuilder
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect
import org.publicvalue.multiplatform.oidc.flows.CodeAuthFlowFactory
import org.publicvalue.multiplatform.oidc.tokenstore.AndroidSettingsTokenStore

@OptIn(ExperimentalOpenIdConnect::class)
fun initApp(
    context: Context,
    codeAuthFlowFactory: CodeAuthFlowFactory,
) = initApp(
    tokenStore = AndroidSettingsTokenStore(context),
    codeAuthFlowFactory = codeAuthFlowFactory,
    databaseBuilder = getDatabaseBuilder(context),
    createDataStore = { createDataStore(context, it) },
    extraModules = listOf(workerModule, androidViewModelModule)
)