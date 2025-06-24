import java.util.*;

public class spiltwise1 {

    static enum ExpenseType {
        EQUAL,
        EXACT,
        PERCENTAGE
    }

    static class User {
        String id;
        String name;

        User(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static class Split {
        User user;
        double amount;

        Split(User user, double amount) {
            this.user = user;
            this.amount = amount;
        }
    }

    static class Expense {
        User paidBy;
        double amount;
        List<Split> splits;
        ExpenseType expenseType;

        Expense(User paidBy, double amount, List<Split> splits, ExpenseType expenseType) {
            this.paidBy = paidBy;
            this.amount = amount;
            this.splits = splits;
            this.expenseType = expenseType;
        }
    }

    static class Expenseservice {
        Map<String, Map<String, Double>> balancesheet = new HashMap<>();

        void addexpense(ExpenseType type, double amount, User paidBy, List<Split> splits) {
            if (type == ExpenseType.EQUAL) {
                double splitamount = amount / splits.size();
                for (Split split : splits) {
                    split.amount = splitamount;
                }
            }

            for (Split split : splits) {
                if (!split.user.id.equals(paidBy.id)) {
                    balancesheet.putIfAbsent(split.user.id, new HashMap<>());
                    balancesheet.putIfAbsent(paidBy.id, new HashMap<>());

                    Map<String, Double> userMap = balancesheet.get(split.user.id);
                    Map<String, Double> paidByMap = balancesheet.get(paidBy.id);

                    userMap.put(paidBy.id, userMap.getOrDefault(paidBy.id, 0.0) + split.amount);
                    paidByMap.put(split.user.id, paidByMap.getOrDefault(split.user.id, 0.0) - split.amount);
                }
            }
        }

        void ShowBalance(Map<String, User> userMap) {
            boolean isEmpty = true;
            for (String user1 : balancesheet.keySet()) {
                for (String user2 : balancesheet.get(user1).keySet()) {
                    double amount = balancesheet.get(user1).get(user2);
                    if (amount > 0) {
                        isEmpty = false;
                        System.out.println(userMap.get(user1).name + " owes " + userMap.get(user2).name + ": " + String.format("%.2f", amount));
                    }
                }
            }
            if (isEmpty) {
                System.out.println("NO Balance");
            }
        }
    }

    public static void main(String[] args) {
        User U1 = new User("U1", "shruti");
        User U2 = new User("U2", "satyam");
        User U3 = new User("U3", "sumit");
        User U4 = new User("U4", "saurabh");
        User U5 = new User("U5", "soni  ");
        User U6 = new User("U6", "yash");

        Map<String, User> userMap = new HashMap<>();
        userMap.put(U1.id, U1);
        userMap.put(U2.id, U2);
        userMap.put(U3.id, U3);
        userMap.put(U4.id, U4);
        userMap.put(U5.id, U5);
        userMap.put(U6.id, U6);

        Expenseservice expenseservice = new Expenseservice();

        List<Split> splits1 = Arrays.asList(
            new Split(U1, 0),
            new Split(U2, 0),
            new Split(U3, 0),
            new Split(U4, 0),
            new Split(U5, 0),
            new Split(U6, 0)
        );
        expenseservice.addexpense(ExpenseType.EQUAL, 500, U1, splits1);

        List<Split> splits2 = Arrays.asList(
            new Split(U2, 0),
            new Split(U3, 0),
            new Split(U4, 0),
            new Split(U5, 0)
        );
        expenseservice.addexpense(ExpenseType.EQUAL, 800, U2, splits2);

        expenseservice.ShowBalance(userMap);
    }
}
