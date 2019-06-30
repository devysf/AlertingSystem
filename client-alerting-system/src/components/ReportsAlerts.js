import React, { Component } from "react";
import axios from "axios";

import {XYPlot, XAxis, YAxis, HorizontalGridLines,VerticalGridLines,LineSeries} from 'react-vis';
import '../../node_modules/react-vis/dist/style.css';


export default class ReportsAlerts extends Component {
  constructor() {
    super();

    this.state = {
     alert : {},
     result:[1,1,1,1,1,1]
    };
  }
  componentDidMount(){
    const { id } = this.props.match.params

    axios
      .get(`http://localhost:8080/api/alerts/${id}`)
      .then(res => {
          this.setState({alert : res.data});
          
          var temp = res.data.result.split(",");
          this.setState({result: temp});
        })
  }

  render() {
    var {name} = this.state.alert;
    var {result} = this.state;
    

    return (
      <div>
        <h1>{name}</h1>
        <h2>{result}</h2>

        <XYPlot
                    xType="ordinal"

                width={300}
                height={300}>
                <VerticalGridLines />
                <HorizontalGridLines />
                <XAxis />
                <YAxis />
                <LineSeries
                    data={[
                        {x: 1, y: result[0] },
                        {x: 2, y: result[1] },
                        {x: 3, y: result[2] },
                        {x: 4, y: result[3]},
                        {x: 5, y: result[4]},
                        {x: 6, y: result[5]}
                    ]}/>
            </XYPlot>
      </div>
    );
  }
}