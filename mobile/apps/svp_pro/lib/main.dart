import 'package:flutter/material.dart';
import 'package:web_socket_channel/web_socket_channel.dart';

void main() {
  runApp(const SvpProApp());
}

class SvpProApp extends StatelessWidget {
  const SvpProApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SVP Pro',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepOrange),
        useMaterial3: true,
      ),
      home: const _AvailabilityPage(),
    );
  }
}

class _AvailabilityPage extends StatefulWidget {
  const _AvailabilityPage({super.key});

  @override
  State<_AvailabilityPage> createState() => _AvailabilityPageState();
}

class _AvailabilityPageState extends State<_AvailabilityPage> {
  bool _available = false;
  WebSocketChannel? _channel;
  String? _lastDemand;

  void _toggleAvailability() {
    setState(() {
      _available = !_available;
    });

    final statusText = _available ? 'Disponible' : 'Indisponible';
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('Statut artisan : $statusText')),
    );

    if (_available) {
      _connectWebSocket();
    } else {
      _disconnectWebSocket();
    }
  }

  void _connectWebSocket() {
    _channel = WebSocketChannel.connect(
      // Pour Android emulator, 10.0.2.2 pointe vers l'hôte.
      Uri.parse('ws://10.0.2.2:8080/ws/demand-dispatch'),
    );

    // On envoie un petit message d'identification simple.
    _channel!.sink.add('subscribe:pro');

    _channel!.stream.listen(
      (event) {
        setState(() {
          _lastDemand = event.toString();
        });
      },
      onError: (_) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Erreur WebSocket, tentative de reconnexion ultérieure.')),
        );
      },
    );
  }

  void _disconnectWebSocket() {
    _channel?.sink.close();
    _channel = null;
  }

  @override
  void dispose() {
    _disconnectWebSocket();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final statusText = _available ? 'Disponible' : 'Indisponible';
    final statusColor = _available ? Colors.green : Colors.grey;

    return Scaffold(
      appBar: AppBar(
        title: const Text('SVP Pro'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              'Statut de disponibilité',
              style: Theme.of(context).textTheme.titleLarge,
            ),
            const SizedBox(height: 16),
            Card(
              child: ListTile(
                leading: Icon(
                  _available ? Icons.check_circle : Icons.pause_circle,
                  color: statusColor,
                ),
                title: Text(statusText),
                subtitle: const Text(
                  'Quand vous êtes disponible, vous pouvez recevoir des demandes proches de vous.',
                ),
                trailing: Switch(
                  value: _available,
                  onChanged: (_) => _toggleAvailability(),
                ),
              ),
            ),
            const SizedBox(height: 24),
            ElevatedButton(
              onPressed: () {
                Navigator.of(context).push(
                  MaterialPageRoute<void>(
                    builder: (_) => const _MonthlyCounterPage(),
                  ),
                );
              },
              child: const Text('Mon compteur du mois'),
            ),
            const SizedBox(height: 24),
            Text(
              'Dernière demande reçue',
              style: Theme.of(context).textTheme.titleMedium,
            ),
            const SizedBox(height: 8),
            Card(
              child: ListTile(
                title: Text(_lastDemand ?? 'Aucune demande pour le moment'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _MonthlyCounterPage extends StatelessWidget {
  const _MonthlyCounterPage({super.key});

  @override
  Widget build(BuildContext context) {
    final lines = <_MonthlyLine>[
      const _MonthlyLine(
        dateLabel: '01/03',
        description: 'Plombier — Fuite chauffe-eau',
        amountLabel: '5,00 € HT',
      ),
      const _MonthlyLine(
        dateLabel: '02/03',
        description: 'Électricien — Panne générale',
        amountLabel: '5,00 € HT',
      ),
      const _MonthlyLine(
        dateLabel: '05/03',
        description: 'Serrurier — Porte claquée',
        amountLabel: '5,00 € HT',
      ),
    ];

    return Scaffold(
      appBar: AppBar(
        title: const Text('Mon compteur du mois'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              'Mises en relation facturables (mock)',
              style: Theme.of(context).textTheme.titleMedium,
            ),
            const SizedBox(height: 8),
            Expanded(
              child: ListView.builder(
                itemCount: lines.length,
                itemBuilder: (context, index) {
                  final line = lines[index];
                  return Card(
                    child: ListTile(
                      leading: Text(line.dateLabel),
                      title: Text(line.description),
                      trailing: Text(line.amountLabel),
                    ),
                  );
                },
              ),
            ),
            const SizedBox(height: 8),
            const Text(
              'Total du mois (mock) : 15,00 € HT',
              textAlign: TextAlign.right,
            ),
          ],
        ),
      ),
    );
  }
}

class _MonthlyLine {
  final String dateLabel;
  final String description;
  final String amountLabel;

  const _MonthlyLine({
    required this.dateLabel,
    required this.description,
    required this.amountLabel,
  });
}



