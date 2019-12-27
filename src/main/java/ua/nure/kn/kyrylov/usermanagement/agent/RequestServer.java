package ua.nure.kn.kyrylov.usermanagement.agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.nure.kn.kyrylov.usermanagement.db.classes.utils.DaoFactory;
import ua.nure.kn.kyrylov.usermanagement.db.exception.DatabaseException;
import ua.nure.kn.kyrylov.usermanagement.domain.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class RequestServer extends CyclicBehaviour {
    @Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if (message != null) {
            if (message.getPerformative() == ACLMessage.REQUEST) {
                myAgent.send(createReply(message));
            } else {
                List<User> users = parseMessage(message);
                ((SearchAgent) myAgent).showUsers(users);
            }
        } else {
            block();
        }
    }

    private List<User> parseMessage(ACLMessage message) {
        List<User> users = new LinkedList<>();
        String content = message.getContent();
        if (content != null) {
            StringTokenizer tokenizer = new StringTokenizer(content, ";");
            while (tokenizer.hasMoreElements()) {
                String userInfo = tokenizer.nextToken();
                StringTokenizer stringTokenizer = new StringTokenizer(userInfo, ",");
                String id = stringTokenizer.nextToken();
                String firstName = stringTokenizer.nextToken();
                String lastName = stringTokenizer.nextToken();
                users.add(new User(new Long(id), firstName, lastName, null));
            }
        }
        return users;
    }

    private ACLMessage createReply(ACLMessage message) {
        ACLMessage reply = message.createReply();
        String content = message.getContent();
        StringTokenizer stringTokenizer = new StringTokenizer(content, " ");
        if (stringTokenizer.countTokens() == 2) {
            String firstMame = stringTokenizer.nextToken();
            String lastName = stringTokenizer.nextToken();
            List<User> users = null;
            try {
                users = DaoFactory.getInstance().getUserDao().find(firstMame, lastName);
            } catch (DatabaseException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                users = new ArrayList<>(0);
            }
            StringBuffer buffer = new StringBuffer();
            for (User user : users) {
                buffer.append(user.getId()).append(",");
                buffer.append(user.getFirstName()).append(",");
                buffer.append(user.getLastName()).append(";");
            }
            reply.setContent(buffer.toString());
        }
        return reply;
    }
}
