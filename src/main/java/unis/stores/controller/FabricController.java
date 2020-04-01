package unis.stores.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unis.stores.constants.Constants;
import unis.stores.entities.Fabric;
import unis.stores.result.fabric.CreateFabricResult;
import unis.stores.result.fabric.DeleteFabricResult;
import unis.stores.result.fabric.GetFabricResult;
import unis.stores.result.fabric.UpdateFabricResult;
import unis.stores.services.fabric.FabricService;

import java.util.Map;

@CrossOrigin
@Controller
public class FabricController {

    @Autowired
    private FabricService fabricService;

    @PostMapping("/fabric")
    public ResponseEntity<Object> create(@RequestBody Map<String, String> body) {
        if (!body.containsKey(Constants.FABRIC_IP_LABEL) || !body.containsKey(Constants.FABRIC_SERVICE_PASS_LABEL)
                || !body.containsKey(Constants.FABRIC_NAME_LABEL))
            return ResponseEntity.badRequest().body(new CreateFabricResult(false, "Bad Request", null));

        Fabric fabric = fabricService.createFabric(body.get(Constants.FABRIC_NAME_LABEL), body.get(Constants.FABRIC_IP_LABEL),
                body.get(Constants.FABRIC_SERVICE_PASS_LABEL));

        if (fabric == null)
            return ResponseEntity.badRequest().body(new CreateFabricResult(false, "Error creating the fabric", null));
        else
            return ResponseEntity.ok().body(fabric);
    }

    @PutMapping("/fabric")
    public ResponseEntity<Object> update(@RequestBody Map<String, String> body) {
        if (!body.containsKey(Constants.FABRIC_ID_LABEL) || !body.containsKey(Constants.FABRIC_SERVICE_PASS_LABEL) ||
                !body.containsKey(Constants.FABRIC_IP_LABEL) || !body.containsKey(Constants.FABRIC_NAME_LABEL))
            return ResponseEntity.badRequest().body(new UpdateFabricResult(false, "Bad Request", null));

        try {
            int fabricId = Integer.parseInt(body.get(Constants.FABRIC_ID_LABEL));

            Fabric updatedFabric = fabricService.updateFabric(fabricId, body.get(Constants.FABRIC_NAME_LABEL),
                    body.get(Constants.FABRIC_IP_LABEL), body.get(Constants.FABRIC_SERVICE_PASS_LABEL));

            if (updatedFabric == null)
                return ResponseEntity.badRequest().body(new UpdateFabricResult(false, "Error updating the fabric", null));
            else
                return ResponseEntity.ok().body(updatedFabric);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new UpdateFabricResult(false, "Bad Request", null));
        }
    }

    @DeleteMapping("/fabric/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        if (id == null)
            return ResponseEntity.badRequest().body(new DeleteFabricResult(false, "Bad Request"));

        try {
            int fabricId = Integer.parseInt(id);

            if (fabricService.deleteFabric(fabricId))
                return ResponseEntity.ok().body(new DeleteFabricResult(true, "Fabric deleted successfully"));
            else
                return ResponseEntity.badRequest().body(new DeleteFabricResult(false, "Error deleting the fabric"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DeleteFabricResult(false, "Bad Request"));
        }
    }

    @GetMapping("/fabric")
    public ResponseEntity<Object> index() {
        return ResponseEntity.ok().body(fabricService.getFabrics());
    }

    @GetMapping("/fabric/{id}")
    public ResponseEntity<Object> get(@PathVariable("id") String id) {
        try {
            if (id == null)
                return ResponseEntity.badRequest().body(new GetFabricResult(false, "Bad Request", null));

            int fabricId = Integer.parseInt(id);
            Fabric fabric = fabricService.getFabric(fabricId);

            if (fabric == null)
                return ResponseEntity.badRequest().body(new GetFabricResult(false, "Fabric doesn't exists", null));
            else
                return ResponseEntity.ok().body(fabric);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new GetFabricResult(false, "Bad Request", null));
        }
    }
}
