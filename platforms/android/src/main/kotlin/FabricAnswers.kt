package org.fathens.cordova.plugin.fabric

import java.math.BigDecimal
import java.util.Currency
import org.apache.cordova.CallbackContext
import org.apache.cordova.CordovaPlugin
import org.json.JSONArray
import org.json.JSONObject
import com.crashlytics.android.answers.*

public class FabricAnswers : CordovaPlugin() {
    override fun execute(action: String, data: JSONArray, callbackContext: CallbackContext): Boolean {
        try {
            val method = javaClass.getMethod(action, data.javaClass)
            val runner = method.invoke(this, data) as (() -> Unit)
            cordova.getThreadPool().execute {
                runner()
                callbackContext.success()
            }
            return true
        } catch (e: NoSuchMethodException) {
            return false
        }
    }

    public fun putCustom(event: AnswersEvent<*>, custom: JSONObject) {
        val iterator = custom.keys()
        while (iterator.hasNext()) {
            val key = iterator.next()
            val value = custom.get(key)
            if (value is String)
                event.putCustomAttribute(key, value)
            if (value is Number)
                event.putCustomAttribute(key, value)
        }
    }

    public fun eventPurchase(args: JSONArray): (() -> Unit) {
        val obj = if (args.length() > 0) args.getJSONObject(0) else null
        return {
            val event = PurchaseEvent()
            if (obj != null) {
                if (obj.has("itemPrice"))
                    event.putItemPrice(BigDecimal(obj.getDouble("itemPrice")))
                if (obj.has("currency"))
                    event.putCurrency(Currency.getInstance(obj.getString("currency")))
                if (obj.has("itemName"))
                    event.putItemName(obj.getString("itemName"))
                if (obj.has("itemType"))
                    event.putItemType(obj.getString("itemType"))
                if (obj.has("itemId"))
                    event.putItemId(obj.getString("itemId"))
                if (obj.has("success"))
                    event.putSuccess(obj.getBoolean("success"))
                if (obj.has("custom"))
                    putCustom(event, obj.getJSONObject("custom"))
            }
            Answers.getInstance().logPurchase(event)
        }
    }

    public fun eventAddToCart(args: JSONArray): (() -> Unit) {
        val obj = if (args.length() > 0) args.getJSONObject(0) else null
        return {
            val event = AddToCartEvent()
            if (obj != null) {
                if (obj.has("itemPrice"))
                    event.putItemPrice(BigDecimal(obj.getDouble("itemPrice")))
                if (obj.has("currency"))
                    event.putCurrency(Currency.getInstance(obj.getString("currency")))
                if (obj.has("itemName"))
                    event.putItemName(obj.getString("itemName"))
                if (obj.has("itemType"))
                    event.putItemType(obj.getString("itemType"))
                if (obj.has("itemId"))
                    event.putItemId(obj.getString("itemId"))
                if (obj.has("custom"))
                    putCustom(event, obj.getJSONObject("custom"))
            }
            Answers.getInstance().logAddToCart(event)
        }
    }

    public fun eventStartCheckout(args: JSONArray): (() -> Unit) {
        val obj = if (args.length() > 0) args.getJSONObject(0) else null
        return {
            val event = StartCheckoutEvent()
            if (obj != null) {
                if (obj.has("totalPrice"))
                    event.putTotalPrice(BigDecimal(obj.getDouble("totalPrice")))
                if (obj.has("currency"))
                    event.putCurrency(Currency.getInstance(obj.getString("currency")))
                if (obj.has("itemCount"))
                    event.putItemCount(obj.getInt("itemCount"))
                if (obj.has("custom"))
                    putCustom(event, obj.getJSONObject("custom"))
            }
            Answers.getInstance().logStartCheckout(event)
        }
    }

    public fun eventContentView(args: JSONArray): (() -> Unit) {
        val obj = if (args.length() > 0) args.getJSONObject(0) else null
        return {
            val event = ContentViewEvent()
            if (obj != null) {
                if (obj.has("contentName"))
                    event.putContentName(obj.getString("contentName"))
                if (obj.has("contentType"))
                    event.putContentType(obj.getString("contentType"))
                if (obj.has("contentId"))
                    event.putContentId(obj.getString("contentId"))
                if (obj.has("custom"))
                    putCustom(event, obj.getJSONObject("custom"))
            }
            Answers.getInstance().logContentView(event)
        }
    }

    public fun eventSearch(args: JSONArray): (() -> Unit) {
        val obj = if (args.length() > 0) args.getJSONObject(0) else null
        return {
            val event = SearchEvent()
            if (obj != null) {
                if (obj.has("query"))
                    event.putQuery(obj.getString("query"))
                if (obj.has("custom"))
                    putCustom(event, obj.getJSONObject("custom"))
            }
            Answers.getInstance().logSearch(event)
        }
    }

    public fun eventShare(args: JSONArray): (() -> Unit) {
        val obj = if (args.length() > 0) args.getJSONObject(0) else null
        return {
            val event = ShareEvent()
            if (obj != null) {
                if (obj.has("method"))
                    event.putMethod(obj.getString("method"))
                if (obj.has("contentName"))
                    event.putContentName(obj.getString("contentName"))
                if (obj.has("contentType"))
                    event.putContentType(obj.getString("contentType"))
                if (obj.has("contentId"))
                    event.putContentId(obj.getString("contentId"))
                if (obj.has("custom"))
                    putCustom(event, obj.getJSONObject("custom"))
            }
            Answers.getInstance().logShare(event)
        }
    }

    public fun eventRating(args: JSONArray): (() -> Unit) {
        val obj = if (args.length() > 0) args.getJSONObject(0) else null
        return {
            val event = RatingEvent()
            if (obj != null) {
                if (obj.has("rating"))
                    event.putRating(obj.getInt("rating"))
                if (obj.has("contentName"))
                    event.putContentName(obj.getString("contentName"))
                if (obj.has("contentType"))
                    event.putContentType(obj.getString("contentType"))
                if (obj.has("contentId"))
                    event.putContentId(obj.getString("contentId"))
                if (obj.has("custom"))
                    putCustom(event, obj.getJSONObject("custom"))
            }
            Answers.getInstance().logRating(event)
        }
    }

    public fun eventSignUp(args: JSONArray): (() -> Unit) {
        val obj = if (args.length() > 0) args.getJSONObject(0) else null
        return {
            val event = SignUpEvent()
            if (obj != null) {
                if (obj.has("method"))
                    event.putMethod(obj.getString("method"))
                if (obj.has("success"))
                    event.putSuccess(obj.getBoolean("success"))
                if (obj.has("custom"))
                    putCustom(event, obj.getJSONObject("custom"))
            }
            Answers.getInstance().logSignUp(event)
        }
    }

    public fun eventLogin(args: JSONArray): (() -> Unit) {
        val obj = if (args.length() > 0) args.getJSONObject(0) else null
        return {
            val event = LoginEvent()
            if (obj != null) {
                if (obj.has("method"))
                    event.putMethod(obj.getString("method"))
                if (obj.has("success"))
                    event.putSuccess(obj.getBoolean("success"))
                if (obj.has("custom"))
                    putCustom(event, obj.getJSONObject("custom"))
            }
            Answers.getInstance().logLogin(event)
        }
    }

    public fun eventInvite(args: JSONArray): (() -> Unit) {
        val obj = if (args.length() > 0) args.getJSONObject(0) else null
        return {
            val event = InviteEvent()
            if (obj != null) {
                if (obj.has("method"))
                    event.putMethod(obj.getString("method"))
                if (obj.has("custom"))
                    putCustom(event, obj.getJSONObject("custom"))
            }
            Answers.getInstance().logInvite(event)
        }
    }

    public fun eventLevelStart(args: JSONArray): (() -> Unit) {
        val obj = if (args.length() > 0) args.getJSONObject(0) else null
        return {
            val event = LevelStartEvent()
            if (obj != null) {
                if (obj.has("levelName"))
                    event.putLevelName(obj.getString("levelName"))
                if (obj.has("custom"))
                    putCustom(event, obj.getJSONObject("custom"))
            }
            Answers.getInstance().logLevelStart(event)
        }
    }

    public fun eventLevelEnd(args: JSONArray): (() -> Unit) {
        val obj = if (args.length() > 0) args.getJSONObject(0) else null
        return {
            val event = LevelEndEvent()
            if (obj != null) {
                if (obj.has("levelName"))
                    event.putLevelName(obj.getString("levelName"))
                if (obj.has("score"))
                    event.putScore(obj.getInt("score"))
                if (obj.has("success"))
                    event.putSuccess(obj.getBoolean("success"))
                if (obj.has("custom"))
                    putCustom(event, obj.getJSONObject("custom"))
            }
            Answers.getInstance().logLevelEnd(event)
        }
    }

    public fun eventCustom(args: JSONArray): (() -> Unit) {
        val obj = if (args.length() > 0) args.getJSONObject(0) else null
        return {
            val event = CustomEvent(obj?.getString("name") ?: "NoName")
            if (obj != null) {
                if (obj.has("attributes"))
                    putCustom(event, obj.getJSONObject("attributes"))
            }
            Answers.getInstance().logCustom(event)
        }
    }
}
