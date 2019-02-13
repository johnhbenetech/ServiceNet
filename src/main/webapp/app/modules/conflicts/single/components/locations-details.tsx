import React from 'react';
import '../single-record-view.scss';
import { connect } from 'react-redux';
import { IActivity } from 'app/shared/model/activity.model';
import { SingleLocationDetails } from './single-location-details';
import { ILocationRecord } from 'app/shared/model/location-record.model';

export interface ILocationsDetailsProp extends StateProps, DispatchProps {
  activity: IActivity;
}

export interface ILocationsDetailsState {
  locations: ILocationRecord[];
  locationsNumber: number;
}

export class LocationsDetails extends React.Component<ILocationsDetailsProp, ILocationsDetailsState> {
  state: ILocationsDetailsState = {
    locations: this.props.activity.record.locations,
    locationsNumber: 0
  };

  changeRecord = () => {
    let locationsNumber = 0;
    if (this.state.locationsNumber !== this.state.locations.length - 1) {
      locationsNumber = this.state.locationsNumber + 1;
    }
    this.setState({ locationsNumber });
  };

  render() {
    const { locations, locationsNumber } = this.state;
    const location = locations[locationsNumber];
    const locationDetails =
      locations.length > locationsNumber ? (
        <SingleLocationDetails
          {...this.props}
          changeRecord={this.changeRecord}
          isOnlyOne={locations.length <= 1}
          location={location.location}
          physicalAddress={location.physicalAddress ? location.physicalAddress : {}}
          postalAddress={location.postalAddress ? location.postalAddress : {}}
          hours={location.regularScheduleOpeningHours}
          locationsCount={`(${locationsNumber + 1}/${locations.length}) `}
        />
      ) : null;

    return locationDetails;
  }
}

const mapStateToProps = () => ({});

const mapDispatchToProps = {};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LocationsDetails);