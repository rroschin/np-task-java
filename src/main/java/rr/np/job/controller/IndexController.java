package rr.np.job.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

  @GetMapping("/")
  public ResourceSupport index() {
    ResourceSupport index = new ResourceSupport();
    index.add(linkTo(HotelSearchController.class).withRel("hotels"));
    return index;
  }
}
