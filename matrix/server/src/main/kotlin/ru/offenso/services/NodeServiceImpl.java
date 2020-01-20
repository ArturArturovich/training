package ru.offenso.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.offenso.entities.Node;
import ru.offenso.repositories.NodeRepository;

@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    private NodeRepository nodeRepository;


    @Override
    public Node createNode() {
        return new Node();
    }

    @Override
    public void update(Node node) {
        nodeRepository.update(node);
    }

    @Override
    public Node findOneById(Integer nodeId) {
        return nodeRepository.findOneById(nodeId);
    }

    @Override
    public Node getParent(Node n) {
        return nodeRepository.getParent(n);
    }

    @Override
    public void setParent(Node n) {
        n.setParent(this.getParent(n));

    }


    @Override
    public void deleteNode(Node node) {

    }
}

