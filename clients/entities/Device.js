const uuid4 = require("uuid4");

const { ApiClient } = require("../apiClient");

class Device {
  id = uuid4();
  status = "";
  registrStatus = "";
  name = "";
  token = "";
  apiClient = new ApiClient("http://localhost:1234");
  constructor(name) {
    this.status = "offline";
    this.name = name;
  }

  registration = async () => {
    try {
      const body = { deviceID: this.uuid, name: this.name };
      const { data } = await this.apiClient.post(
        `api/v1/deviceRegistr/registration`,
        body
      );
      this.token = data.token;
      this.registrStatus = data.registStatus;
    } catch (e) {
      console.log("Error", e);
    }
  };

  delete = async () => {
    try {
      const { data } = await this.apiClient.delete(
        `api/v1/deviceRegistr/delete/${this.uuid}`,
        body
      );
      console.log(data);
    } catch (e) {
      console.log("Error", e);
    }
  };

  changeStatus = async () => {
    try {
      const body = { deviceID: this.uuid, status: "online" };
      const { data } = await this.apiClient.patch(
        "api/v1/device/changeStatus",
        body
      );
      console.log(data);
    } catch (e) {
      console.log("Error", e);
    }
  };

  getStatus = async () => {
    try {
      const { data } = await this.apiClient.get(
        `api/v1/device/status/${this.uuid}`
      );
      console.log(data);
    } catch (e) {
      console.log("Error", e);
    }
  };
}

module.exports = { Device };
