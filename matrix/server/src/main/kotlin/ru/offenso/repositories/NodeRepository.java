package ru.offenso.repositories;


import org.springframework.stereotype.Repository;
import ru.offenso.entities.Node;

import java.util.List;


@Repository
public interface NodeRepository {

    public Node createNode();

    public void update(Node n);

    public Node findOneById(Integer nodeId);

    public Node getParent(Node n);

    public void deleteNode(Node n);

}
