package ru.offenso.repositories;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.offenso.entities.Node;

public class NodeRepositoryImpl implements NodeRepository {

    private static final Logger logger = LoggerFactory.getLogger(NodeRepositoryImpl.class);

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Node createNode() {
        return new  Node();
    }

    @Override
    public void update(Node n) {
        Session session = sessionFactory.getCurrentSession();
        session.update(n);

    }

    @Override
    public Node findOneById(Integer nodeId) {
        return findOneById(nodeId);
    }

    @Override
    public Node getParent(Node n) {
        return n;
}

    @Override
    public void deleteNode(Node n) {

    }
}
