package fi.matiaspaavilainen.masuiteportals;

import fi.matiaspaavilainen.masuitecore.config.Configuration;
import fi.matiaspaavilainen.masuitecore.managers.Location;
import fi.matiaspaavilainen.masuiteportals.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Portal {

    private Database db = MaSuitePortals.db;
    private Connection connection = null;
    private PreparedStatement statement = null;
    private Configuration config = new Configuration();
    private String tablePrefix = config.load(null, "config.yml").getString("database.table-prefix");

    private String name;
    private String server;
    private String type;
    private String destination;
    private String fillType;
    private Location minLoc, maxLoc;

    public Portal(){}

    public Portal(String name, String server, String type, String destination, String fillType, Location minLoc, Location maxLoc) {
        this.name = name;
        this.server = server;
        this.type = type;
        this.destination = destination;
        this.fillType = fillType;
        this.minLoc = minLoc;
        this.maxLoc = maxLoc;
    }

    public void save(){
        String insert = "INSERT INTO " + tablePrefix +
                "portals (name, server, type, destination, filltype, world, minX, minY, minZ, maxX, maxY, maxZ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE server = ?, type = ?, destination = ?, filltype = ?, world = ?, minX = ?, minY = ?, minZ = ?, maxX = ?, maxY = ?, maxZ = ?;";
        try {
            connection = db.hikari.getConnection();
            statement = connection.prepareStatement(insert);
            statement.setString(1, getName());
            statement.setString(2, getServer());
            statement.setString(3, getType());
            statement.setString(4, getDestination());
            statement.setString(5, getFillType());
            statement.setString(6, getMinLoc().getWorld());
            statement.setDouble(7, getMinLoc().getX());
            statement.setDouble(8, getMinLoc().getY());
            statement.setDouble(9, getMinLoc().getZ());
            statement.setDouble(10, getMaxLoc().getX());
            statement.setDouble(11, getMaxLoc().getY());
            statement.setDouble(12, getMaxLoc().getZ());
            statement.setString(13, getServer());
            statement.setString(14, getType());
            statement.setString(15, getDestination());
            statement.setString(16, getFillType());
            statement.setString(17, getMinLoc().getWorld());
            statement.setDouble(18, getMinLoc().getX());
            statement.setDouble(19, getMinLoc().getY());
            statement.setDouble(20, getMinLoc().getZ());
            statement.setDouble(21, getMaxLoc().getX());
            statement.setDouble(22, getMaxLoc().getY());
            statement.setDouble(23, getMaxLoc().getZ());
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFillType() {
        return fillType;
    }

    public void setFillType(String fillType) {
        this.fillType = fillType;
    }

    public Location getMinLoc() {
        return minLoc;
    }

    public void setMinLoc(Location minLoc) {
        this.minLoc = minLoc;
    }

    public Location getMaxLoc() {
        return maxLoc;
    }

    public void setMaxLoc(Location maxLoc) {
        this.maxLoc = maxLoc;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}