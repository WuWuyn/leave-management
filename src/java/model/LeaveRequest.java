/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.*;


/**
 *
 * @author admin
 */
public class LeaveRequest {
    
    private int id;
    private String title;
    private LeaveType type;
    private String reason;
    private Date startDate;
    private Date endDate;
    private String attachment;
    private String status;
    private User createdBy;
    private Timestamp createdDate;
    private Employee owner;
    private User approvalBy;
    private Timestamp approvalDate;
    private String comments;

    public LeaveRequest(int id, String title, LeaveType type, String reason, Date startDate, Date endDate, String attachment, String status, User createdBy, Timestamp createdDate, Employee owner, User approvalBy, Timestamp approvalDate, String comments) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.reason = reason;
        this.startDate = startDate;
        this.endDate = endDate;
        this.attachment = attachment;
        this.status = status;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.owner = owner;
        this.approvalBy = approvalBy;
        this.approvalDate = approvalDate;
        this.comments = comments;
    }

    public LeaveRequest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LeaveType getType() {
        return type;
    }

    public void setType(LeaveType type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Employee getOwner() {
        return owner;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    public User getApprovalBy() {
        return approvalBy;
    }

    public void setApprovalBy(User approvalBy) {
        this.approvalBy = approvalBy;
    }

    public Timestamp getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Timestamp approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    

}
