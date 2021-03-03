package Model;

public class Subscriptions extends ActiveRecordBase {

    private Integer id_user;
    private Integer id_forum;

    public Subscriptions() {
        _builtFromDB = false;
    }

    public Subscriptions(Integer id_user, Integer id_forum) {
        this.id_user = id_user;
        this.id_forum = id_forum;
        _builtFromDB = true;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getId_forum() {
        return id_forum;
    }

    public void setId_forum(Integer id_forum) {
        this.id_forum = id_forum;
    }

    @Override
    protected String _update() {
        return null;

    }

    @Override
    protected String _insert() {
        return "INSERT INTO `db_sr03`.`subscriptions` (`id_user`, `id_forum`) "
                + "VALUES ('" + id_user + "', '" + id_forum + "');";
    }

    @Override
    protected String _delete() {
        return "DELETE FROM `db_sr03`.`subscriptions` WHERE (`id_user` = '" + id_user + "' AND `id_forum` = '" + id_forum + "');";
    }


}
