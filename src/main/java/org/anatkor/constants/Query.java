package org.anatkor.constants;

public class Query {

    public static final String FIND_ALL_USERS = "SELECT * FROM usr;";
    public static final String ADD_USER =
            "INSERT INTO usr (last_name, name, middle_name, password, email, phone_number) VALUES (?,?,?,?,?,?)";
    public static final String UPDATE_USER_STATUS = "UPDATE usr SET active=? WHERE id=?";
    public static final String ADD_USER_ROLE = "INSERT INTO user_role VALUES (?,?)";
    public static final String FIND_USER_BY_PHONE_NUMBER = "SELECT * FROM usr WHERE phone_number=?";
    public static final String FIND_USER_BY_PHONE_NUMBER_AND_PASSWORD =
            "SELECT * FROM usr WHERE phone_number=? AND password=?;";
    public static final String FIND_ROLE_BY_USERID = "SELECT * FROM user_role WHERE user_id=?";
    public static final String ADD_USER_AND_ROLE =
            "INSERT INTO usr (username, email, password)  VALUES (?,?,?); INSERT INTO user_role(user_id, role) VALUES ((SELECT id FROM usr WHERE login ='?'), 'USER')";
    public static final String FIND_USER_BY_ID = "SELECT * FROM usr WHERE id=?";
    public static final String FIND_USER_FULL_NANE_BY_ACCONT_NUMBER =
            "SELECT last_name, usr.name, middle_name FROM usr INNER JOIN account ON account.user_id=usr.id WHERE number = ?";

    public static final String FIND_MAX_ACCOUNT_NUMBER = "SELECT MAX(number) FROM account";
    public static final String ADD_ACCOUNT =
            "INSERT INTO account (number, balance, account_name, currency, user_id, action) VALUES (?,0,?,?,?,1);";
    public static final String FIND_ACCOUNTS_BY_USER_ID_SORTED = "SELECT * FROM account WHERE user_id=? ORDER BY ? ?";
    public static final String FIND_ACCOUNTS_SORTED = "SELECT * FROM account ORDER BY ? ?";
    public static final String FIND_ACCOUNT_BY_ID = "SELECT * FROM account WHERE id=?";
    public static final String FIND_ACCOUNT_WITH_CARD_BY_ID =
            "SELECT * FROM account LEFT JOIN credit_card cc on account.id = cc.account_id where account.id=?";
    public static final String FIND_ACCOUNT_WITH_CARD_BY_NUMBER =
            "SELECT * FROM account LEFT JOIN credit_card cc on account.id = cc.account_id where account.number=?";
    public static final String FIND_ALL_ACCOUNTS_TO_DO = "SELECT * FROM account WHERE action>0";
    public static final String UPDATE_ACCOUNT_ACTIVE_BY_ID = "UPDATE account SET active=?, action=0 WHERE id=?";
    public static final String UPDATE_ACCOUNT_BALANCE_BY_ID = "UPDATE account SET balance=balance+? WHERE id=?";
    public static final String UPDATE_ACCOUNT_ACTION_BY_ID = "UPDATE account SET action=? WHERE id=?";
    public static final String ADD_CREDIT_CARD_ACCOUNT = "INSERT INTO credit_card(account_id) VALUES (?)";
    public static final String UPDATE_ACCOUNT_BALANCE = "UPDATE account SET balance=balance+? WHERE number=?";

    public static final String ADD_TRANSACTION =
            "INSERT INTO transaction (payer, receiver, destination, amount, currency) VALUES(?,?,?,?,?)";
}
