import 'dart:async';

import 'package:flutter/material.dart';
import 'package:time_tracker_flutter/page_info.dart';
import 'package:time_tracker_flutter/tree.dart' as Tree hide getTree;
// to avoid collision with an Interval class in another library
import 'package:time_tracker_flutter/requests.dart';

class PageIntervals extends StatefulWidget {
  final int id; // final because StatefulWidget is immutable
  bool isActive;
  PageIntervals(this.id, this.isActive);

  @override
  _PageIntervalsState createState() => _PageIntervalsState(this.isActive);
}

class _PageIntervalsState extends State<PageIntervals> {
  late int id;
  late Future<Tree.Tree> futureTree;
  late Timer _timer;
  late bool isActive;

  static const int periodeRefresh = 2; // better a multiple of periode in TimeTracker, 2 seconds

  _PageIntervalsState(this.isActive);


  void _onDetailsClick(id) {
    print("Click");
    Navigator.of(context)
        .push(MaterialPageRoute<void>(builder: (context) => PageInfo(id)));
  }

  void _refresh() async {
    futureTree = getTree(id);
    setState(() {});
  }

  void _activateTimer() {
    _timer = Timer.periodic(Duration(seconds: periodeRefresh), (Timer t) {
      futureTree = getTree(id);
      setState(() {});
    });
  }

  @override
  void dispose() {
    // "The framework calls this method when this State object will never build again"
    // therefore when going up
    _timer.cancel();
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    id = widget.id;
    futureTree = getTree(id);
    _activateTimer();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Tree.Tree>(
      future: futureTree,
      // this makes the tree of children, when available, go into snapshot.data
      builder: (context, snapshot) {
        // anonymous function
        if (snapshot.hasData) {
          int numChildren = snapshot.data!.root.children.length;
          return Scaffold(
            appBar: AppBar(
              title: Text(snapshot.data!.root.name),
              actions: <Widget>[
                IconButton(icon: Icon(Icons.info_outline_rounded),
                  onPressed: (){
                    _onDetailsClick(this.id);
                  },
                ),
                IconButton(icon: Icon(Icons.home),
                  onPressed: () {
                    Navigator.popUntil(context, ModalRoute.withName('/'));
                  },
                ),
              ],
            ),
            body: ListView.separated(
              // it's like ListView.builder() but better because it includes a separator between items
              padding: const EdgeInsets.all(16.0),
              itemCount: numChildren,
              itemBuilder: (BuildContext context, int index) =>
                  _buildRow(snapshot.data!.root.children[index], index),
              separatorBuilder: (BuildContext context, int index) =>
              const Divider(),
            ),
            floatingActionButton: FloatingActionButton(
              backgroundColor: Colors.blue,
              child: this.isActive ? Icon(Icons.pause_rounded) : Icon(Icons.play_arrow_rounded),
              onPressed: () {
                if (this.isActive) {
                  stop(this.id);
                  this.isActive = false;
                } else {
                  start(this.id);
                  this.isActive = true;
                }
                _refresh();
              },
            ),
          );
        } else if (snapshot.hasError) {
          return Text("${snapshot.error}");
        }
        // By default, show a progress indicator
        return Container(
            height: MediaQuery.of(context).size.height,
            color: Colors.white,
            child: Center(
              child: CircularProgressIndicator(),
            ));
      },
    );
  }

  Widget _buildRow(Tree.Interval interval, int index) {
    String sDuration = Duration(seconds: interval.duration).toString().split('.').first;
    String sInitialDate = interval.initialDate.toString().split('.')[0];
    String sLastDate = interval.lastDate.toString().split('.')[0];

    TextStyle currentStyle;
    if (interval.active){
      currentStyle = TextStyle(color: Colors.green,);
    }
    else {
      currentStyle = TextStyle(color: Colors.black,);
    }
    return ListTile(
      title: Text('from ${sInitialDate} to ${sLastDate}', style: currentStyle,),
      trailing: Text('$sDuration',style: currentStyle,),
    );

  }
}
