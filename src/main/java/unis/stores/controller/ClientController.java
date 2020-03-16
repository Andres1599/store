package unis.stores.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unis.stores.constants.Constants;
import unis.stores.entities.Client;
import unis.stores.result.client.CreateClientResult;
import unis.stores.result.client.DeleteClientResult;
import unis.stores.result.client.GetClientResult;
import unis.stores.result.client.UpdateClientResult;
import unis.stores.services.client.ClientService;

import java.util.Map;

@CrossOrigin
@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/client")
    public ResponseEntity<Object> create(@RequestBody Map<String, String> body) {
        if (!body.containsKey(Constants.CLIENT_NAME_LABEL) || !body.containsKey(Constants.CLIENT_EMAIL_LABEL) ||
            !body.containsKey(Constants.CLIENT_NIT_LABEL) || !body.containsKey(Constants.CLIENT_PHONE_LABEL) ||
            !body.containsKey(Constants.CLIENT_IMAGE_LABEL) || !body.containsKey(Constants.CLIENT_SUBSCRIPTION_LABEL))
            return ResponseEntity.badRequest().body(new CreateClientResult(false, "Bad Request"));

        if (clientService.searchByNit(body.get(Constants.CLIENT_NIT_LABEL)) != null)
            return ResponseEntity.badRequest().body(new CreateClientResult(false, "The client already exists"));

        try {
            int subscriptionId = Integer.parseInt(body.get(Constants.CLIENT_SUBSCRIPTION_LABEL));

            Client createdClient = clientService.createClient(body.get(Constants.CLIENT_NAME_LABEL), body.get(Constants.CLIENT_NIT_LABEL),
                    body.get(Constants.CLIENT_EMAIL_LABEL), body.get(Constants.CLIENT_PHONE_LABEL), body.get(Constants.CLIENT_IMAGE_LABEL), subscriptionId);

            if (createdClient == null)
                return ResponseEntity.badRequest().body(new CreateClientResult(false, "Error creating the new client"));
            else
                return ResponseEntity.ok().body(createdClient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CreateClientResult(false, "Bad Request"));
        }
    }

    @PutMapping("/client")
    public ResponseEntity<Object> update(@RequestBody Map<String, String> body) {
        if (!body.containsKey(Constants.CLIENT_ID_LABEL) || !body.containsKey(Constants.CLIENT_NAME_LABEL) ||
                !body.containsKey(Constants.CLIENT_NIT_LABEL) || !body.containsKey(Constants.CLIENT_EMAIL_LABEL) ||
                !body.containsKey(Constants.CLIENT_PHONE_LABEL) || !body.containsKey(Constants.CLIENT_IMAGE_LABEL) ||
                !body.containsKey(Constants.CLIENT_SUBSCRIPTION_LABEL))
            return ResponseEntity.badRequest().body(new UpdateClientResult(false, "Bad Request"));

        if (clientService.searchByNit(body.get(Constants.CLIENT_NIT_LABEL)) != null)
            return ResponseEntity.badRequest().body(new UpdateClientResult(false, "The client already exists"));

        try {

            Client updatedClient = clientService.updateClient(Integer.parseInt(body.get(Constants.CLIENT_SUBSCRIPTION_LABEL)), body.get(Constants.CLIENT_NAME_LABEL),
                    body.get(Constants.CLIENT_NIT_LABEL), body.get(Constants.CLIENT_EMAIL_LABEL), body.get(Constants.CLIENT_PHONE_LABEL),
                    body.get(Constants.CLIENT_IMAGE_LABEL), Integer.parseInt(body.get(Constants.CLIENT_ID_LABEL)));

            if (updatedClient == null)
                return ResponseEntity.badRequest().body(new UpdateClientResult(false, "Error updating the client"));
            else
                return ResponseEntity.ok().body(updatedClient);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new UpdateClientResult(false, "Bad Request"));
        }
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        if (id == null)
            return ResponseEntity.badRequest().body(new DeleteClientResult(false, "Bad Requests"));

        try {
            int clientId = Integer.parseInt(id);

            if (clientService.deleteClient(clientId)) {
                return ResponseEntity.ok().body(new DeleteClientResult(true, "Client deleted successfully"));
            } else
                return ResponseEntity.badRequest().body(new DeleteClientResult(false, "Error deleting the client"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DeleteClientResult(false, "Bad Requests"));
        }
    }

    @GetMapping("/client")
    public ResponseEntity<Object> index() {
        return ResponseEntity.ok().body(clientService.getClients());
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") String id) {
        try {
            int clientId = Integer.parseInt(id);

            Client client = clientService.getClientById(clientId);

            if (client == null)
                return ResponseEntity.badRequest().body(new GetClientResult(false, "Client doesn't exists"));
            else
                return ResponseEntity.ok().body(client);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new GetClientResult(false, "Bad Request"));
        }
    }
}
