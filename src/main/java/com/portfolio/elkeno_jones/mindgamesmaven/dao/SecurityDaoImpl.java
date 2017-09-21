package com.portfolio.elkeno_jones.mindgamesmaven.dao;

import com.portfolio.elkeno_jones.mindgamesmaven.model.SecurityObj;
import com.portfolio.elkeno_jones.mindgamesmaven.util.ConnectionUtils;
import com.portfolio.elkeno_jones.mindgamesmaven.util.DbUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Elkeno
 */
public class SecurityDaoImpl implements SecurityDao {

    protected static SimpleDateFormat sdf
            = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public SecurityObj getSecurityAccess(Integer userId) {
        sdf.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        SecurityObj sec = null;

        PreparedStatement ps = null;
        String sql = null;
        Connection conn = null;
        try {
            conn = ConnectionUtils.getConnection();

            sql = "SELECT * FROM security_monitor where userId = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("userId");
                String sesToken = rs.getString("sessionToken");
                Long storedTime = rs.getLong("timeRemaining");

                sec = new SecurityObj(id, sesToken, storedTime);
            }
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            DbUtils.close(ps, conn);
        }

        return sec;
    }

    @Override
    public SecurityObj updateInsertTokenAccess(Integer userId, String token) {
        SecurityObj sec = null;
        PreparedStatement ps = null;
        String sql = null;
        Connection conn = null;
        Date curDt = new Date();

        try {
            conn = ConnectionUtils.getConnection();
            sql = "SELECT * FROM security_monitor where userId = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            int rsCount = 0;
            while (rs.next()) {
                rsCount++;
                int id = rs.getInt("userId");
                String sesToken = rs.getString("sessionToken");
                Long storedTime = rs.getLong("timeRemaining");

                sec = new SecurityObj(id, sesToken, storedTime);
            }

            if (rsCount > 0) {
                sql = "UPDATE `security_monitor`"
                        + " SET `timeRemaining`=?, `sessionToken`=?"
                        + " WHERE userId = ?";

                ps = conn.prepareStatement(sql);
                ps.setLong(1, curDt.getTime());
                ps.setString(2, token);
                ps.setInt(3, userId);
            } else {
                sql = "INSERT INTO `security_monitor`"
                        + " (`userId`, `sessionToken`, `timeRemaining`) "
                        + "VALUES (?,?,?)";

                ps = conn.prepareStatement(sql);
                ps.setInt(1, userId);
                ps.setString(2, token);
                ps.setLong(3, curDt.getTime());
            }
            ps.executeUpdate();
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            DbUtils.close(ps, conn);
        }

        return sec;
    }

    @Override
    public boolean deleteTokenEntry(Integer userId) {
        PreparedStatement ps = null;
        String sql = null;
        Connection conn = null;
        boolean isSuccess = false;

        try {
            conn = ConnectionUtils.getConnection();

            sql = "SELECT * FROM security_monitor where userId = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            int rsCount = 0;
            while (rs.next()) {
                rsCount++;
            }

            if (rsCount > 0) {
                sql = "UPDATE `security_monitor`"
                        + " SET `timeRemaining`=?, `sessionToken`=?"
                        + " WHERE userId = ?";

                ps = conn.prepareStatement(sql);
                ps.setLong(1, 0L);
                ps.setString(2, "0");
                ps.setInt(3, userId);
            }
            ps.executeUpdate();
            isSuccess = true;
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            e.printStackTrace();
        } finally {
            DbUtils.close(ps, conn);
        }

        return isSuccess;
    }
}
