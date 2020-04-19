package unis.stores.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unis.stores.constants.Constants;
import unis.stores.entities.Rol;
import unis.stores.entities.Subscription;
import unis.stores.result.rol.GetRolResult;
import unis.stores.result.subscription.CreateSubscriptionResult;
import unis.stores.result.subscription.DeleteSubscriptionResult;
import unis.stores.result.subscription.GetSubscriptionResult;
import unis.stores.result.subscription.UpdateSubscriptionResult;
import unis.stores.services.subscription.SubscriptionService;

import java.util.Map;

@CrossOrigin
@Controller
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscription")
    public ResponseEntity<Object> create(@RequestBody Map<String, String> body) {
        if (!body.containsKey(Constants.SUBSCRIPTION_NAME_LABEL) || !body.containsKey(Constants.SUBSCRIPTION_DISCOUNT_LABEL))
            return ResponseEntity.badRequest().body(new CreateSubscriptionResult(false, "Bad Request", null));

        try {
            int discount = Integer.parseInt(body.get(Constants.SUBSCRIPTION_DISCOUNT_LABEL));
            Subscription subscription = subscriptionService.createSubscription(body.get(Constants.SUBSCRIPTION_NAME_LABEL), discount);

            if (subscription == null)
                return ResponseEntity.badRequest().body(new CreateSubscriptionResult(false, "Error creating the subscription", null));
            else
                return ResponseEntity.ok().body(new CreateSubscriptionResult(true, "Subscription created", subscription));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CreateSubscriptionResult(false, "Bad Request", null));
        }
    }

    @PutMapping("/subscription")
    public ResponseEntity<UpdateSubscriptionResult> update(@RequestBody Map<String, String> body) {
        if (!body.containsKey(Constants.SUBSCRIPTION_NAME_LABEL) || !body.containsKey(Constants.SUBSCRIPTION_ID_LABEL)
                || !body.containsKey(Constants.SUBSCRIPTION_DISCOUNT_LABEL))
            return ResponseEntity.badRequest().body(new UpdateSubscriptionResult(false, "Bad Request", null));

        try {
            int subscriptionId = Integer.parseInt(body.get(Constants.SUBSCRIPTION_ID_LABEL));
            int discount = Integer.parseInt(body.get(Constants.SUBSCRIPTION_DISCOUNT_LABEL));
            Subscription updatedSubscription = subscriptionService.updateSubscription(subscriptionId, body.get(Constants.SUBSCRIPTION_NAME_LABEL), discount);

            if (updatedSubscription == null)
                return ResponseEntity.badRequest().body(new UpdateSubscriptionResult(false, "Error updating the subscription", null));
            else
                return ResponseEntity.ok().body(new UpdateSubscriptionResult(true, "Subscription updated", updatedSubscription));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new UpdateSubscriptionResult(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/subscription/{id}")
    public ResponseEntity<DeleteSubscriptionResult> delete(@PathVariable("id") String id) {
        if (id == null)
            return ResponseEntity.badRequest().body(new DeleteSubscriptionResult(false, "Bad Request"));

        try {
            int subscriptionId = Integer.parseInt(id);

            if (subscriptionService.deleteSubscription(subscriptionId))
                return ResponseEntity.ok().body(new DeleteSubscriptionResult(true, "Subscription deleted"));
            else
                return ResponseEntity.badRequest().body(new DeleteSubscriptionResult(false, "Error deleting the subscription"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DeleteSubscriptionResult(false, "Bad Request"));
        }
    }

    @GetMapping("/subscription")
    public ResponseEntity<Object> index() {
        return ResponseEntity.ok().body(subscriptionService.getSubscriptions());
    }

    @GetMapping("/subscription/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") String id) {
        if (id == null)
            return ResponseEntity.badRequest().body(new GetSubscriptionResult(false, "Bad Request"));

        try {
            int subscriptionId = Integer.parseInt(id);

            Subscription subscription = subscriptionService.getSubscriptionById(subscriptionId);

            if (subscription == null)
                return ResponseEntity.badRequest().body(new GetSubscriptionResult(false, "Subscription doesn't exists"));
            else
                return ResponseEntity.ok().body(subscription);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new GetSubscriptionResult(false, e.getMessage()));
        }
    }
}
