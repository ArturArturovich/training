package ru.offenso.services;

import org.springframework.stereotype.Controller;
import ru.offenso.entities.Node;

@Controller
public interface NodeService {

    public Node createNode();

    public void update(Node node);

    public Node findOneById(Integer nodeId);

    public Node getParent(Node n);

    public void setParent(Node n);

    public void deleteNode(Node node);
}
