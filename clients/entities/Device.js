const { apiClient } = require("../apiClient");

class Device {
  id = "";
  status = "";
  registrStatus = "";
  name = "";
  token = "";
  constructor(name, uuid) {
    this.status = "offline";
    this.id = uuid;
    this.name = name;
  }

  registration = async () => {
    try {
      const body = { deviceID: this.uuid, name: this.name };
      const { data } = await apiClient.post(
        `api/v1/deviceRegistr/registration`,
        body
      );
      this.token = data.token;
      this.registrStatus = data.registStatus;
    } catch (e) {
      console.log(e);
    }
  };

  delete = async () => {
    try {
      const { data } = await apiClient.delete(
        `api/v1/deviceRegistr/delete/${this.uuid}`,
        body
      );
      console.log(data);
    } catch (e) {
      console.log(e);
    }
  };

  changeStatus = async () => {
    try {
      const body = { deviceID: this.uuid, status: "online" };
      const { data } = await apiClient.patch(
        "api/v1/device/changeStatus",
        body
      );
      console.log(data);
    } catch (e) {
      console.log(e);
    }
  };

  getStatus = async () => {
    try {
      const { data } = await apiClient.get(`api/v1/device/status/${this.uuid}`);
      console.log(data);
    } catch (e) {
      console.log(e);
    }
  };
}

module.exports = { Device };
