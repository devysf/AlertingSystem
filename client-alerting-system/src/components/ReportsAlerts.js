import React, { Component } from "react";
import axiosApi from "../axios-config/axios";

import {XYPlot, XAxis, YAxis, HorizontalGridLines,VerticalGridLines,LineSeries} from 'react-vis';
import '../../node_modules/react-vis/dist/style.css';


export default class ReportsAlerts extends Component {
  constructor() {
    super();

    this.state = {
     loading:false,
     alert : {},
     tableResult:[1,1,1,1,1,1],
     tableX:[10,20,3,4,5,6]
    };
  }
  componentDidMount(){
    if (!localStorage.getItem("jwtToken")) {
      this.props.history.push('/login');
    }
        const setIntervalTime = this.props.location.state[0].period;

        setInterval(this.getResultsInDatabase.bind(this), setIntervalTime);
      
  }

  getResultsInDatabase(){
    const { id } = this.props.match.params

    axiosApi
      .get(`http://localhost:8080/api/alerts/${id}`)
      .then(res => {
          this.setState({alert : res.data});
          this.setState({loading:true});
        
          //another aproach to show alert status 
          var length = res.data.results.length;
          var sliced = res.data.results.slice(length-6, length).map(res=>{return res.status});

          var sliced2 = res.data.results.slice(length-6, length).map(res=>{return res.date});

          
          this.setState({tableResult : sliced});
          this.setState({tableX :sliced2 })
        })
        .catch(err=> {

          console.log("errr" + err);
        })
  }

  render() {
    var {name} = this.state.alert;
    var {tableResult,tableX,loading} = this.state;
    
    const graph = (
            <div >
             <h1>{name}</h1>

            <XYPlot
                    xType="ordinal"

                width={500}
                height={300}>
                <VerticalGridLines />
                <HorizontalGridLines />
                <XAxis />
                <YAxis />
                <LineSeries
                    data={[
                        {x: tableX[0], y: tableResult[0]},
                        {x: tableX[1], y: tableResult[1] },
                        {x: tableX[2], y: tableResult[2] },
                        {x: tableX[3], y: tableResult[3]},
                        {x: tableX[4], y: tableResult[4]},
                        {x: tableX[5], y: tableResult[5]}
                    ]}/>
            </XYPlot>
            </div>
            
    )
    return (

      <div className="container">
        {loading==true ? graph : <h1>Loading</h1> }

      </div>
      
    );
  }
}