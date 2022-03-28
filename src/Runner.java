import by.training.gym.model.Subscription;
import by.training.gym.model.User;
import by.training.gym.service.ServiceException;
import by.training.gym.service.SubscriptionService;
import by.training.gym.service.UserService;

import java.util.List;

public class Runner {
    public static void main (String[] args) throws ServiceException {
        UserService service = new UserService();
        List<User> users = service.findClientByName("Goncharov");
        for (User user : users
             ) {
            System.out.println(user);
        }
        SubscriptionService service1 = new SubscriptionService();
        List<Subscription> subscriptions = service1.findAllClientSubscriptions(1);
        for (Subscription sub: subscriptions
             ) {
            System.out.println(sub);
        }
    }
}
