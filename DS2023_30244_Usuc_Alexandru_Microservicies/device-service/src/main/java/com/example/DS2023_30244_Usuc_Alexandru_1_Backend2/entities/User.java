package com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.entities;

import com.example.DS2023_30244_Usuc_Alexandru_1_Backend2.dtos.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {

    @Id
    private long id;

    @NotNull
    @Column
    private String username;

    /*@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @JoinTable(name = "userdevice",
        joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "device_id", referencedColumnName = "id")
        })
    @JsonIgnore
    @JsonManagedReference*/
    @XmlTransient
    @OneToMany(targetEntity = Device.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "idUser", referencedColumnName = "id")
    @NotNull
    private List<Device> deviceList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setDeviceList(user.getDeviceList());
        return userDTO;
    }
}
