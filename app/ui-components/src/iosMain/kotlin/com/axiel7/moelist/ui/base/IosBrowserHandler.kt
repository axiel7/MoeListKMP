package com.axiel7.moelist.ui.base

import platform.Foundation.NSURL
import platform.SafariServices.SFSafariViewController
import platform.UIKit.UIApplication

class IosBrowserHandler : BrowserHandler {
    override fun launchUrl(url: String) {
        val url = NSURL(string = url)
        val safariViewController = SFSafariViewController(uRL = url)
        val viewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        viewController?.presentViewController(
            safariViewController,
            animated = true,
            completion = null
        )
    }
}