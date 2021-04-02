package org.iushu.project.controller;

import org.iushu.project.bean.Actor;
import org.iushu.project.service.ActorService;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * WebMvc.fn
 *
 * Spring Web MVC includes WebMvc.fn, a lightweight functional programming model in which functions are used to route
 * and handle requests and contracts are designed for immutability. The RouterFunction is used to route the requests
 * to the corresponding HandlerFunction.
 *
 * @see org.iushu.project.ProjectConfiguration#routerFunction(ActorService)
 *
 * Components for support WebMvc.fn
 * @see org.springframework.web.servlet.function.RouterFunction
 * @see org.springframework.web.servlet.function.HandlerFunction
 * @see org.springframework.web.servlet.function.ServerRequest
 * @see org.springframework.web.servlet.function.ServerResponse
 *
 * Responsible to handle HandlerFunction methods, like RequestMappingHandlerAdapter handle RequestMapping methods.
 * @see org.springframework.web.servlet.function.support.HandlerFunctionAdapter
 *
 * @author iuShu
 * @since 4/2/21
 */
public class ActorRouterHandler {

    private ActorService actorService;

    public ActorRouterHandler(ActorService actorService) {
        this.actorService = actorService;
    }

    /**
     * An HandlerFunction (noted the definition of the handler function method)
     * sample url: http://../pro/router/parameter?key=val&key2=val2.
     */
    public ServerResponse parameters(ServerRequest request) {
        MultiValueMap parameters = request.params();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(parameters);
    }

    public ServerResponse bodyActor(ServerRequest request) throws ServletException, IOException {
        Actor actor = request.body(Actor.class);
        actor.setFirst_name(actor.getFirst_name() + "-router");
        actor.setLast_name(actor.getLast_name() + "-router");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(actor);
    }

    public ServerResponse getActor(ServerRequest request) {
        String attr = request.pathVariable("actor_id");
        Actor actor = attr == null || attr.length() < 1 ? new Actor() : actorService.getActor(Short.valueOf(attr));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(actor);
    }

    public ServerResponse listActor(ServerRequest request) {
        String variable = request.pathVariable("pageNo");
        int pageNo = variable == null || variable.length() < 1 ? 1 : Integer.valueOf(variable);
        List<Actor> actors = this.actorService.getActors(pageNo, 10);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(actors);
    }

}
