import { Link, useNavigate, useParams } from "react-router-dom";
import useFetch from "./UseFetch";
import { Button, Modal } from "@material-ui/core";
import ModifyUserData from "./ModifyUserData";
import { useState, useEffect, useRef, useMemo } from "react";
import axios from "axios";
import { Line } from "react-chartjs-2";
import Chart from "react-apexcharts";
import moment from "moment";
import { useLocation } from 'react-router-dom';

const DeviceDetails = () => {

  const [measurements, setMeasurements] = useState([]);
  const { id } = useParams();
  const navigate = useNavigate("");

  // Declare initial state for the chart
  const [state, setState] = useState({
    options: {
      chart: {
        id: "basic-bar",
      },
      xaxis: {
        categories: [],
      },
    },
    series: [
      {
        name: "series-1",
        data: [],
      },
    ],
  });

  useEffect(() => {
    // Extract timestamps and measurement values from measurements array
    // const timestamps = measurements.map((measurement) => measurement.timestamp);
    //   const timestamps = measurements.map((measurement) =>
    //   moment(new Date(measurement.timestamp)).format("YYYY-MM-DD HH:mm:ss")
    // );

    const rawTimestamps = measurements.map(
      (measurement) => measurement.timestamp
    );
    // console.log("Raw Timestamps:", rawTimestamps);

    const timestamps = measurements.map((measurement) =>
      moment(Number(measurement.timestamp)).format("YYYY-MM-DD HH:mm:ss")
    );
    // console.log("Formatted Timestamps:", timestamps);

    const measurementValues = measurements.map(
      (measurement) => measurement.measurement_value
    );

    // Update state with new data
    setState({
      options: {
        chart: {
          id: "basic-bar",
        },
        xaxis: {
          categories: timestamps,
        },
      },
      series: [
        {
          name: "series-1",
          data: measurementValues,
        },
      ],
    });
  }, [measurements]);

  const {
    data: device,
    isPending,
    error,
  } = useFetch("http://localhost:8081/readDevices/" + id, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });

  useEffect(() => {
    axios.get("http://localhost:8082/getMeasurements/" + id, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    }).then((respose) => {
      setMeasurements(respose.data);
    });
  }, [id]);

  return (
    <div className="device-details">
      <br></br>
      <h2>Device Details</h2>
      <br></br>
      <br></br>
      {device && (
        <article>
          <h3>Id: {device.id}</h3>
          <h4>Name: {device.name}</h4>
          <h4>Type: {device.type}</h4>
        </article>
      )}

      <Link to={`/modifydevicedata/${id}`}>
        <Button>Edit</Button>
      </Link>

      {/* <br></br>
      <br></br>
      <br></br>
      <h4>Measurements</h4>
      {measurements &&
        measurements.map((measurementObj) => (
          <div className="container">
            <div className="blog-preview" key={measurementObj.id}>
              <h3>Id: {measurementObj.id}</h3>
              <h5>Timestamp: {measurementObj.timestamp}</h5>
              <h5>Measurement Value: {measurementObj.measurement_value}</h5>
            </div>
          </div>
        ))} */}

      <br></br>
      <br></br>
      <div style={{ display: "flex", justifyContent: "center" }}>
        <Chart
          options={state.options}
          series={state.series}
          type="area"
          width="760"
        />
      </div>
    </div>
  );
};

export default DeviceDetails;
