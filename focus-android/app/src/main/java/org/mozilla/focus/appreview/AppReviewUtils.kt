/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.focus.appreview

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.preference.PreferenceManager
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import mozilla.components.browser.state.state.SessionState
import org.mozilla.focus.R
import org.mozilla.focus.ext.components
import org.mozilla.focus.state.AppAction
import org.mozilla.focus.utils.SupportUtils
import java.util.concurrent.TimeUnit

class AppReviewUtils {
    companion object {
        /**
         * Number of app openings until In App Review is triggered.
         */
        private const val APP_OPENINGS_REVIEW_TRIGGER = 3
        private val APP_REVIEW_TIME_TRIGGER = TimeUnit.DAYS.toMillis(90)

        /**
         * Shows in app review or opens play store if Review Info task is not successful.
         *
         * @property activity where In App review is triggered
         */
        @Suppress("UNUSED_PARAMETER")
        fun showAppReview(activity: Activity) {

        }

        /**
         * Set the number of app openings and the flag when In App Review is needed.
         *
         * @property context needed for SharePref
         */
        fun addAppOpenings(context: Context) {
            val preferenceManage = PreferenceManager.getDefaultSharedPreferences(context)
            val currentOpeningsNumber = preferenceManage.getInt(
                context.getString(
                    R.string.pref_in_app_review_openings,
                ),
                0,
            ) + 1
            val appReviewStep = preferenceManage.getString(
                context.getString(
                    R.string.pref_in_app_review_step,
                ),
                AppReviewStep.Pending.name,
            )

            preferenceManage
                .edit().putInt(
                    context.getString(R.string.pref_in_app_review_openings),
                    currentOpeningsNumber,
                ).apply()
            if (currentOpeningsNumber == APP_OPENINGS_REVIEW_TRIGGER &&
                appReviewStep == AppReviewStep.Pending.name
            ) {
                setAppReviewStep(context, AppReviewStep.ReviewNeeded)
            }
        }

        private fun shouldShowInAppReview(context: Context): Boolean {
            val inAppReviewStep = PreferenceManager.getDefaultSharedPreferences(context).getString(
                context.getString(R.string.pref_in_app_review_step),
                AppReviewStep.Pending.name,
            )
            val lastReviewedTime = PreferenceManager.getDefaultSharedPreferences(context).getLong(
                context.getString(R.string.pref_in_app_review_time),
                0L,
            )

            return inAppReviewStep == AppReviewStep.ReviewNeeded.name || (
                lastReviewedTime +
                    APP_REVIEW_TIME_TRIGGER <= System.currentTimeMillis() &&
                    inAppReviewStep == AppReviewStep.Reviewed.name
                )
        }

        @Suppress("UNUSED_PARAMETER")
        private fun openPlayStore(activity: Activity) {
        }

        private fun setAppReviewed(activity: Activity) {
            setAppReviewStep(activity, AppReviewStep.Reviewed)
            setLastReviewedTime(activity)
        }

        private fun setAppReviewStep(context: Context, appReviewStep: AppReviewStep) {
            PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(
                    context.getString(R.string.pref_in_app_review_step),
                    appReviewStep.name,
                ).apply()
        }

        private fun setLastReviewedTime(context: Context) {
            PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putLong(
                    context.getString(R.string.pref_in_app_review_time),
                    System.currentTimeMillis(),
                ).apply()
        }
    }
}
