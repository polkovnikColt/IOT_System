const { ApiClient } = require("../apiClient");

const apiClientInner = new ApiClient("http://localhost:1234");

class Device {
  uuid = "";
  status = "";
  registrStatus = "";
  name = "";
  token = "";
  apiClient = apiClientInner;
  constructor(name, uuid) {
    this.uuid = uuid;
    this.status = "offline";
    this.name = name;
  }

  registration = async () => {
    try {
      const body = { deviceID: this.uuid };
      console.log(`Connecting device with uuid ${this.uuid}`);
      const { data } = await this.apiClient.post(
        `api/v1/deviceRegistr/registration`,
        body
      );
      this.token = data.token;
      this.apiClient.insertToken(data.token);
      this.registrStatus = data.registStatus;
      console.log(
        `Registration is successful with status ${this.registrStatus}`
      );
    } catch (e) {
      if (e) {
        console.log("Error", e);
      }
    }
  };

  create = async () => {
    try {
      const body = { deviceID: this.uuid, name: this.name };
      console.log(`Creating device with uuid ${this.uuid}`);
      const { data } = await this.apiClient.post(`api/v1/device/create`, body);
      console.log(`Created device ${data}`);
    } catch (e) {
      if (e) {
        console.log("Error", e);
      }
    }
  };

  delete = async () => {
    try {
      console.log(`Deleting device with uuid ${this.uuid}`);
      const { data } = await this.apiClient.delete(
        `api/v1/deviceRegistr/delete/${this.uuid}`,
        body
      );
      console.log(`Deleted device ${data}`);
    } catch (e) {
      if (e) {
        console.log("Error", e);
      }
    }
  };

  changeStatus = async (status) => {
    try {
      console.log(
        `Changing status of device with uuid ${this.uuid}, name ${this.name}`
      );
      const body = { deviceID: this.uuid, status };
      const { data } = await this.apiClient.patch(
        "api/v1/device/changeStatus",
        body
      );
      console.log(`Device with uuid ${this.uuid} status now is ${data}`);
    } catch (e) {
      if (e) {
        console.log("Error", e);
      }
    }
  };

  getStatus = async () => {
    try {
      console.log(`Getting status of device with uuid ${this.uuid}`);
      const { data } = await this.apiClient.get(
        `api/v1/device/status/${this.uuid}`
      );
      console.log(`Device with uuid ${this.uuid} status is ${data}`);
    } catch (e) {
      if (e) {
        console.log("Error", e);
      }
    }
  };
}

module.exports = { Device };
