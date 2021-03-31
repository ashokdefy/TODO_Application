package com.deloitte.todo_app.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table
@NoArgsConstructor
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String header;

    @Column
    private Date date;

    @Column
    private String description;

    @Column
    private boolean checkBox;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public TodoList(String header, boolean checkBox, User user, String description) {
        this.header = header;
        this.checkBox = checkBox;
        this.user = user;
        this.description = description;
    }

    @Override
    public String toString() {
        return "TodoList{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", checkBox=" + checkBox +
                ", user=" + user +
                '}';
    }

}
